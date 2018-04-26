package ca.bc.northvan.armintoussi.contactbook.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ca.bc.northvan.armintoussi.contactbook.Models.Contact;

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
        super(context, ContactBookDatabaseContract.DATABASE_NAME,
                null, ContactBookDatabaseContract.SCHEMA_VERSION);
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
        db.execSQL(ContactBookDatabaseContract.PersonTable.CREATE_PERSON_TABLE);
        db.execSQL(ContactBookDatabaseContract.AddressTable.CREATE_ADDRESS_TABLE);
        db.execSQL(ContactBookDatabaseContract.ContactTable.CREATE_CONTACT_TABLE);
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
        db.execSQL(ContactBookDatabaseContract.ContactTable.DELETE_CONTACT_TABLE);
        onCreate(db);
    }

    //TODO delete this code, it's just there to check that entries are actually being put in to the database.
    public long getNumberOfContacts(final SQLiteDatabase db) {
        final long numEntries;

        numEntries = DatabaseUtils.queryNumEntries(db, ContactBookDatabaseContract.ContactTable.TABLE_NAME);

        return numEntries;
    }

    /**
     * Gets all contacts from the contact Table
     * but on
     *
     * @param context app context.
     * @param db a database reference.
     *
     * @return cursor with results.
     */
    public Cursor getAllContactsWithPersons(final Context context, final SQLiteDatabase db) {
        final Cursor cursor;

        final String query =
                  "SELECT * FROM " + ContactBookDatabaseContract.ContactTable.TABLE_NAME
                + " INNER JOIN "   + ContactBookDatabaseContract.PersonTable.TABLE_NAME
                + " ON "           + ContactBookDatabaseContract.ContactTable.TABLE_NAME + "."
                                   + ContactBookDatabaseContract.ContactTable.PERSON_ID + " = "
                                   + ContactBookDatabaseContract.PersonTable.TABLE_NAME + "."
                                   + ContactBookDatabaseContract.PersonTable._ID
                + " ORDER BY " + ContactBookDatabaseContract.PersonTable.F_NAME + " ASC";

        cursor = db.rawQuery(query, null);

        cursor.setNotificationUri(context.getContentResolver(),
                ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_URI);

        return cursor;
    }
}
