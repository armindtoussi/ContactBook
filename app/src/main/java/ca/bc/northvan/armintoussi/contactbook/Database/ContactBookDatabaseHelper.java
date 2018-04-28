package ca.bc.northvan.armintoussi.contactbook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ca.bc.northvan.armintoussi.contactbook.Models.Contact;

import static ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract.*;

/**
 * Created by armin2 on 4/20/2018.
 *
 * Database open helper for accessing the sqlite database
 * in this app.
 * The helper is accessed through the content provider
 * in all cases ideally.
 *
 */
public class ContactBookDatabaseHelper extends SQLiteOpenHelper {
    /** Debug class tag. */
    private static final String TAG = ContactBookDatabaseHelper.class.getName();
    /** Instance of this helper. */
    private static ContactBookDatabaseHelper instance;
    /** Database Instance. */
    private SQLiteDatabase db;

    /**
     * Inits the database, private as we don't want instantiation.
     * Use getInstance method instead.
     * @param context the context.
     */
    private ContactBookDatabaseHelper(final Context context) {
        super(context, DATABASE_NAME, null, SCHEMA_VERSION);
        db = getWritableDatabase();
    }

    /**
     * Method used to create an instance of this helper.
     * Safely instantiates ContactBookDatabaseHelper.
     *
     * @param context the context.
     * @return an instance of 'this'.
     */
    public synchronized static ContactBookDatabaseHelper getInstance(final Context context) {
        if(instance == null) {
            Log.i(TAG, "instance is null trying to create new");
            instance = new ContactBookDatabaseHelper(context.getApplicationContext());
        }
        return instance;
    }

    /**
     * Configures the database on load.
     *
     * @param db an instance fo the db.
     */
    @Override
    public void onConfigure(final SQLiteDatabase db) {
        super.onConfigure(db);
        Log.i(TAG, "#$#$#$#$#$CONFIGURE$$%%$%%%$$%$%");
        setWriteAheadLoggingEnabled(true);
        db.setForeignKeyConstraintsEnabled(true);
    }

    /**
     * Creates the database.
     * Called during database creation.
     *
     * @param db the database ref.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Creating the tables");
        db.execSQL(PersonTable.CREATE_PERSON_TABLE);
        db.execSQL(AddressTable.CREATE_ADDRESS_TABLE);
        db.execSQL(ContactTable.CREATE_CONTACT_TABLE);
    }

    /**
     * Run when we need to upgrade the database version.
     * Shouldn't ever be used in this application.
     *
     * @param db an instance of the db.
     * @param oldVersion old version int.
     * @param newVersion new version int.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(ContactTable.DELETE_CONTACT_TABLE);
        onCreate(db);
    }

    //TODO delete this code, it's just there to check that entries are actually being put in to the database.
    public long getNumberOfContacts(final SQLiteDatabase db) {
        final long numEntries;

        numEntries = DatabaseUtils.queryNumEntries(db, ContactTable.TABLE_NAME);

        return numEntries;
    }

    /**
     * Gets all contacts from the contact Table with Person table
     * joined to it so we can get names.
     *
     * @param context app context.
     * @param db a database reference.
     *
     * @return cursor with results.
     */
    public Cursor getAllContactsWithPersons(final Context context, final SQLiteDatabase db) {
        final Cursor cursor;

        final String query =
                  "SELECT * FROM " + ContactTable.TABLE_NAME
                + " INNER JOIN "   + PersonTable.TABLE_NAME
                + " ON "           + ContactTable.TABLE_NAME + "."
                                   + ContactTable.PERSON_ID  + " = "
                                   + PersonTable.TABLE_NAME  + "."
                                   + PersonTable._ID
                + " ORDER BY " + PersonTable.F_NAME + " ASC";

        cursor = db.rawQuery(query, null);

        cursor.setNotificationUri(context.getContentResolver(),
                ContactTable.CONTACT_CONTENT_URI);

        return cursor;
    }

    /**
     * Gets a single contact from the Contact table joined with
     * both Address and Person table so we have all contact information.
     *
     * @param context app context.
     * @param db a database reference.
     * @param id the id of the contact to search for.
     *
     * @return a cursor with single row of results.
     */
    public Cursor getSingleFullContact(final Context context, final SQLiteDatabase db, final long id) {
        final Cursor cursor;

        final String query =
                  "SELECT * FROM " + ContactTable.TABLE_NAME
                + " INNER JOIN "   + PersonTable.TABLE_NAME
                + " ON "           + ContactTable.TABLE_NAME + "."
                                   + ContactTable.PERSON_ID  + " = "
                                   + PersonTable.TABLE_NAME  + "."
                                   + PersonTable._ID
                + " INNER JOIN "   + AddressTable.TABLE_NAME
                + " ON "           + ContactTable.TABLE_NAME + "."
                                   + ContactTable.ADDRESS_ID + " = "
                                   + AddressTable.TABLE_NAME + "."
                                   + AddressTable._ID
                + " WHERE "        + ContactTable.TABLE_NAME + "."
                                   + ContactTable._ID
                + " = "            + id;
        cursor = db.rawQuery(query, null);

        cursor.setNotificationUri(context.getContentResolver(), ContactTable.CONTACT_ITEM_URI);

        return cursor;
    }

    /**
     * Updates an entry in the Person table.
     *
     * @param db a database reference.
     * @param id the id of the Person to update.
     * @param cv the values to insert.
     *
     * @return the number of rows affected.
     */
    public int updateSinglePerson(final SQLiteDatabase db, final long id, final ContentValues cv) {
        return db.update(PersonTable.TABLE_NAME, cv, "_id="+id, null);
    }

    /**
     * Updates an entry in the Address table.
     *
     * @param db a database reference.
     * @param id the id of the Address to update.
     * @param cv the values to insert.
     *
     * @return the number of rows affected.
     */
    public int updateSingleAddress(final SQLiteDatabase db, final long id, final ContentValues cv) {
        return db.update(AddressTable.TABLE_NAME, cv, "_id=" + id, null);
    }

    /**
     * Updates an entry in the Contact table.
     *
     * @param db a database reference.
     * @param id the id of the Contact to update.
     * @param cv the values to insert.
     *
     * @return the number of rows affected.
     */
    public int updateSingleContact(final SQLiteDatabase db, final long id, final ContentValues cv) {
        return db.update(ContactTable.TABLE_NAME, cv, "_id=" + id, null);
    }
}
