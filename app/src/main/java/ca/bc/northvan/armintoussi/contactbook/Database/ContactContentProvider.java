package ca.bc.northvan.armintoussi.contactbook.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

/**
 * Created by armin2 on 4/20/2018.
 */

public class ContactContentProvider extends ContentProvider {
    /** Debug tag. */
    private static final String TAG = ContactContentProvider.class.getName();

    /** Person URI code. */
    private static final int PERSON_URI_CODE = 1;
    /** Address URI code. */
    private static final int ADDRSS_URI_CODE = 2;
    /** Contact URI code. */
    private static final int CONTCT_URI_CODE = 3;

    /** Uri Matcher for building the db uri's. */
    private static final UriMatcher uriMatcher;


    /** ContactBookDatabaseHelper instance. */
    private ContactBookDatabaseHelper cbDbHelper;


    //Add uri's to uri matcher.
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
        cbDbHelper = ContactBookDatabaseHelper.getInstance(getContext());
        return cbDbHelper != null;
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

    @Override
    public Cursor query(final Uri uri, final String[] projection, final String selection,
                        final String[] selectionArgs, final String sortOrder) {
        final Cursor cursor;

        switch(uriMatcher.match(uri)) {
            case PERSON_URI_CODE:
                //TODO do stuff maybe.
                break;
            case ADDRSS_URI_CODE:
                //Todo do stuff maybe.
                break;
            case CONTCT_URI_CODE:
                //Todo do stuff maybe.
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        throw new UnsupportedOperationException("Query not fully implemented yet.");
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
        //Todo - implement later if needed.
        throw new UnsupportedOperationException("Insert not yet implemented");
    }
}
