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
 */

public class ContactBookDatabaseHelper extends SQLiteOpenHelper {
    /** Debug tag. */
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

    /**
     * Inserts a contact into the database, including person and address entities.
     *
     * @param db an instance of the db.
     * @param contact the Contact object to insert.
     */
    public long insertContact(final SQLiteDatabase db, final Contact contact) {
        final ContentValues contactContentValues = new ContentValues();
        Log.i(TAG, "INSERT contact.");
        //insert foreign entities first.
        long personID  = insertPerson(db, contact);
        long addressID = insertAddress(db, contact);
        //set Contact table stuffs.
        contactContentValues.put(ContactBookDatabaseContract.ContactTable.PERSON_ID, personID);
        contactContentValues.put(ContactBookDatabaseContract.ContactTable.ADDRESS_ID, addressID);
        contactContentValues.put(ContactBookDatabaseContract.ContactTable.EMAIL, contact.getEmail());
        contactContentValues.put(ContactBookDatabaseContract.ContactTable.MOBILE_PHONE, contact.getMobilePhoneNumber());
        contactContentValues.put(ContactBookDatabaseContract.ContactTable.HOME_PHONE, contact.getHomePhoneNumber());
        //insert contact to db.
        return db.insert(ContactBookDatabaseContract.ContactTable.TABLE_NAME, null, contactContentValues);
    }

    /**
     * inserts a person into the database.
     *
     * @param db an instance of the database.
     * @param contact the Contact containing the Person to insert.
     *
     * @return the id of the person inserted.
     */
    private long insertPerson(final SQLiteDatabase db, final Contact contact) {
        final ContentValues personContentValues  = new ContentValues();
        Log.i(TAG, "INSERT person.");
        //set person table stuffs.
        personContentValues.put(ContactBookDatabaseContract.PersonTable.F_NAME, contact.getPerson().getFirstName());
        personContentValues.put(ContactBookDatabaseContract.PersonTable.M_NAME, contact.getPerson().getMiddleName());
        personContentValues.put(ContactBookDatabaseContract.PersonTable.L_NAME, contact.getPerson().getLastName());
        //insert person table stuffs.
        return db.insert(ContactBookDatabaseContract.PersonTable.TABLE_NAME, null, personContentValues);
    }

    /**
     * inserts an address into the database.
     *
     * @param db an instance of the database.
     * @param contact the contact containing the Address to insert.
     *
     * @return the id of the address inserted.
     */
    private long insertAddress(final SQLiteDatabase db, final Contact contact) {
        final ContentValues addressContentValues = new ContentValues();
        Log.i(TAG, "INSERT ADDY.");
        //set address table stuffs
        addressContentValues.put(ContactBookDatabaseContract.AddressTable.STREET_ADDR, contact.getAddress().getAddrStreetAddress());
        addressContentValues.put(ContactBookDatabaseContract.AddressTable.CITY_ADDR, contact.getAddress().getAddrCity());
        addressContentValues.put(ContactBookDatabaseContract.AddressTable.STATE_ADDR, contact.getAddress().getAddrState());
        addressContentValues.put(ContactBookDatabaseContract.AddressTable.COUNTRY_ADDR, contact.getAddress().getAddrCountry());
        addressContentValues.put(ContactBookDatabaseContract.AddressTable.POST_ADDR, contact.getAddress().getAddrPostCode());
        //Insert address table stuffs.
        return db.insert(ContactBookDatabaseContract.AddressTable.TABLE_NAME, null, addressContentValues);
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
                                   + ContactBookDatabaseContract.PersonTable._ID;

        cursor = db.rawQuery(query, null);
        Log.i(TAG, "context null? " + (context == null));

        cursor.setNotificationUri(context.getContentResolver(),
                ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_URI);

        return cursor;
    }
}
