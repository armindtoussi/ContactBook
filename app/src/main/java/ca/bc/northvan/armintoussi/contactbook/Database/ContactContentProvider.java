package ca.bc.northvan.armintoussi.contactbook.Database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

/**
 * Created by armin2 on 4/20/2018.
 *
 * The content provider for accessing the sqlite
 * database.
 *
 */
public class ContactContentProvider extends ContentProvider {
    /** Debug class tag. */
    private static final String TAG = ContactContentProvider.class.getName();

    /** Person URI code. */
    public static final int PERSON_URI_CODE = 1;
    /** Address URI code. */
    public static final int ADDRSS_URI_CODE = 2;
    /** Contact URI code for getting all contacts. */
    public static final int CONTCT_URI_CODE = 3;

    /** Uri Matcher for building the db uri's. */
    private static final UriMatcher uriMatcher;

    /** ContactBookDatabaseHelper instance. */
    private ContactBookDatabaseHelper dbHelper;


    /** Add the necessary Uri's to the matcher. */
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ContactBookDatabaseContract.AUTHORITY,
                          ContactBookDatabaseContract.PersonTable.TABLE_NAME,
                          PERSON_URI_CODE);
        uriMatcher.addURI(ContactBookDatabaseContract.AUTHORITY,
                          ContactBookDatabaseContract.AddressTable.TABLE_NAME,
                          ADDRSS_URI_CODE);
        uriMatcher.addURI(ContactBookDatabaseContract.AUTHORITY,
                          ContactBookDatabaseContract.ContactTable.TABLE_NAME,
                          CONTCT_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate() is being called");
        dbHelper = ContactBookDatabaseHelper.getInstance(getContext());
        return true;
    }

    /**
     * Handles requests for MIME types for the uri's we provide.
     *
     * @param uri the uri to match.
     *
     * @return the MIME type as a string.
     */
    @Override
    public String getType(final Uri uri) {
        final String type;

        switch(uriMatcher.match(uri)) {
            case PERSON_URI_CODE:
                type = ContactBookDatabaseContract.PersonTable.PERSON_CONTENT_TYPE;
                break;
            case ADDRSS_URI_CODE:
                type = ContactBookDatabaseContract.AddressTable.ADDRESS_CONTENT_TYPE;
                break;
            case CONTCT_URI_CODE:
                type = ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_TYPE;
                break;
            default:
                throw new IllegalArgumentException("Unsupported Uri: " + uri);
        }
        return type;
    }

    /**
     * Queries the database based on a uri and
     * returns a cursor with the requested data.
     *
     * @param uri the uri to match.
     * @param projection list of columns to put in cursor.
     * @param selection  a selection criteria to apply when filtering rows. (null == all rows).
     * @param selectionArgs wild card selections.
     * @param sortOrder  the order to srt the results.
     *
     * @return a cursor loaded with the data.
     */
    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection,
                        final String[] selectionArgs, final String sortOrder) {
        Log.i(TAG, "$#%#%#$%Querying hopefully@#$@#%#%");
        final Cursor cursor;
        //todo- maybe change this to sqlbuilder method of doing query.
        switch(uriMatcher.match(uri)) {
            case PERSON_URI_CODE:
                //TODO do stuff maybe.
                cursor = null;
                break;
            case ADDRSS_URI_CODE:
                //Todo do stuff maybe.
                cursor = null;
                break;
            case CONTCT_URI_CODE:
                //Grab all contacts with persons.
                final SQLiteDatabase db;

                db     = dbHelper.getReadableDatabase();
                cursor = dbHelper.getAllContactsWithPersons(this.getContext(), db);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }

        return cursor;
    }

    /**
     * Method to delete a row from a give table.
     *
     * @param uri the uri to delete from .
     * @param selection restrictive information for the deletion.
     * @param selectionArgs
     *
     * @return number of rows affected as an int.
     */
    @Override
    public int delete(final Uri uri, final String selection, final String[] selectionArgs) {
        //Todo - implement later if needed.
        throw new UnsupportedOperationException("Delete not yet implemented");
    }

    /**
     * Update a given entry in the db.
     *
     * @param uri Uri to update which will contain the table.
     * @param values the values to update.
     * @param selection restrictive information for the update.
     * @param selectionArgs
     *
     * @return number of rows affected as an int.
     */
    @Override
    public int update(final Uri uri, final ContentValues values,
                      final String selection, final String[] selectionArgs) {
        //Todo - implement later if needed.
        throw new UnsupportedOperationException("Update not yet implemented");
    }

    /**
     * Insert a given entry in the db.
     *
     * @param uri Uri to insert which will contain the table.
     * @param values the values to insert.
     *
     * @return the Uri for the newly inserted item.
     */
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        if(dbHelper == null) {
            dbHelper = ContactBookDatabaseHelper.getInstance(getContext());
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long id;

        switch(uriMatcher.match(uri)) {
            case CONTCT_URI_CODE:
                id = db.insert(ContactBookDatabaseContract.ContactTable.TABLE_NAME,
                        null, values);
                return getUriForId(id, uri);
            case PERSON_URI_CODE:
                id = db.insert(ContactBookDatabaseContract.PersonTable.TABLE_NAME,
                        null, values);
                return getUriForId(id, uri);
            case ADDRSS_URI_CODE:
                id = db.insert(ContactBookDatabaseContract.AddressTable.TABLE_NAME,
                        null, values);
                return getUriForId(id, uri);
            default:
                throw new IllegalArgumentException("Unsupported URI for insertion: " + uri);
        }
    }

    /**
     * Gets the uri of an individual element.
     *
     * @param id the id of the entry.
     * @param uri the uri of the table.
     *
     * @return the new uri of the individual element.
     *
     * @throws SQLException if the id is a fail code.
     */
    private Uri getUriForId(final long id, final Uri uri) throws SQLException {
        if (id > 0) {
            Uri itemUri = ContentUris.withAppendedId(uri, id);
            //getContext().getContentResolver().notifyChange(itemUri, null);
            return itemUri;
        }
        throw new SQLException("Problem while inserting into uri: " + uri);
    }
}
