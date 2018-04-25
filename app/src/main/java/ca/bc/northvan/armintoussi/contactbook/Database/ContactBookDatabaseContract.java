package ca.bc.northvan.armintoussi.contactbook.Database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by armin2 on 4/20/2018.
 *
 * The SQLite contract.
 * Holds all the constants we need for the database and
 * abstract static classes that represent each table and
 * the constants required for each table.
 *
 */
public class ContactBookDatabaseContract {
    /** Class debugg tag. */
    private static final String TAG = ContactBookDatabaseContract.class.getName();

    /** The current db schema version no. */
    public  static final int SCHEMA_VERSION = 1;
    /** The database name. */
    public  static final String DATABASE_NAME  = "contactbook.db";
    /** The authority, present in the provider section of manifest. */
    public  static final String AUTHORITY = "ca.bc.northvan.armintoussi.contactbook.contactDatabase";
    /** Scheme of the uri. */
    public  static final String SCHEME = "content://";
    /** A front slash constant. */
    public  static final String SLASH = "/";
    /** SQL query constant for text type. */
    private static final String TEXT_TYPE = " TEXT";
    /** SQL query constant for not null. */
    private static final String NOT_NULL = " NOT NULL";
    /** A comma separator constant. */
    private static final String COMMA_SEP = ",";
    private static final String MIME_TRPE_PREFIX = "/vnd.";


    /**
     * This is a non-instantiable class so we have
     * a private ctor.
     */
    private ContactBookDatabaseContract() { }

    /**
     * Database contract for Person class.
     */
    public static abstract class PersonTable implements BaseColumns {
        /** Table Name. */
        public static final String TABLE_NAME = "Person";
        /** Column Names. */
        public static final String F_NAME     = "firstName";
        public static final String M_NAME     = "middleName";
        public static final String L_NAME     = "lastName";

        /** Content Uri for the Person Table. */
        public static final Uri PERSON_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + SLASH + TABLE_NAME);
        public static final Uri PERSON_ITEM_URI    = Uri.parse(SCHEME + AUTHORITY + SLASH + TABLE_NAME + SLASH + "#");

        /**The MIME type of Person Uri. */
        public static final String PERSON_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                         MIME_TRPE_PREFIX + AUTHORITY + "." + TABLE_NAME;
        public static final String PERSON_ITEM_TYPE    = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                                                         MIME_TRPE_PREFIX + AUTHORITY + "." + TABLE_NAME;

        /**Person Table creation query. */
        public static final String CREATE_PERSON_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME + " (" +
                _ID        + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                F_NAME     + TEXT_TYPE + NOT_NULL  + COMMA_SEP +
                M_NAME     + TEXT_TYPE + COMMA_SEP +
                L_NAME     + TEXT_TYPE + NOT_NULL  + " )";
        /**Person Table deletion query. */
        public static final String DELETE_PERSON_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Database contract for Address Class.
     */
    public static abstract class AddressTable implements BaseColumns {
        /** Table name. */
        public static final String TABLE_NAME   = "Address";
        /** Column names. */
        public static final String STREET_ADDR  = "addrStreetAddress";
        public static final String CITY_ADDR    = "addrCity";
        public static final String STATE_ADDR   = "addrState";
        public static final String COUNTRY_ADDR = "addrCountry";
        public static final String POST_ADDR    = "addrPostCode";

        /** Content Uri for the Address Table. */
        public static final Uri ADDRESS_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + SLASH + TABLE_NAME);
        /**The MIME type of Person Uri. */
        public static final String ADDRESS_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                          MIME_TRPE_PREFIX + AUTHORITY + "." + TABLE_NAME;

        /**Address Table creation query. */
        public static final String CREATE_ADDRESS_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME   + " (" +
                _ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                STREET_ADDR  + TEXT_TYPE + COMMA_SEP +
                CITY_ADDR    + TEXT_TYPE + COMMA_SEP +
                STATE_ADDR   + TEXT_TYPE + COMMA_SEP +
                COUNTRY_ADDR + TEXT_TYPE + COMMA_SEP +
                POST_ADDR    + TEXT_TYPE + ")";
        /**Address Table deletion query. */
        public static final String DELETE_ADDRESS_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }

    /**
     * Database contract for Contact class.
     */
    public static abstract class ContactTable implements BaseColumns {
        /** Table name. */
        public static final String TABLE_NAME   = "Contact";
        /** Column names. */
        /** Foreign key to Person table. */
        public static final String PERSON_ID    = "personID";
        /** Foreign key to Address table. */
        public static final String ADDRESS_ID   = "addressID";
        public static final String EMAIL        = "email";
        public static final String HOME_PHONE   = "homePhoneNumber";
        public static final String MOBILE_PHONE = "mobilePhoneNumber";

        /** Content Uri for the Contact table. */
        public static final Uri CONTACT_CONTENT_URI = Uri.parse(SCHEME + AUTHORITY + SLASH + TABLE_NAME);
        public static final Uri CONTACT_ITEM_URI    = Uri.parse(SCHEME + AUTHORITY + SLASH + TABLE_NAME + "/#");
        /**The MIME type of Contact Uri. */
        public static final String CONTACT_CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE +
                                                          MIME_TRPE_PREFIX + AUTHORITY + "." + TABLE_NAME;
        public static final String CONTACT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +
                                                       MIME_TRPE_PREFIX + AUTHORITY + "." + TABLE_NAME;


        /**Contact Table creation query. */
        public static final String CREATE_CONTACT_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_NAME   + " (" +
                _ID          + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                PERSON_ID    + " INTEGER," +
                ADDRESS_ID   + " INTEGER," +
                EMAIL        + TEXT_TYPE   + COMMA_SEP +
                HOME_PHONE   + TEXT_TYPE   + COMMA_SEP +
                MOBILE_PHONE + TEXT_TYPE   + COMMA_SEP +
                "FOREIGN KEY (" + PERSON_ID  + ") REFERENCES "
                                + PersonTable.TABLE_NAME
                                + " (" + PersonTable._ID  + ") ON DELETE CASCADE," +
                "FOREIGN KEY (" + ADDRESS_ID + ") REFERENCES "
                                + AddressTable.TABLE_NAME
                                + " (" + AddressTable._ID + ") ON DELETE CASCADE)";
        /**Contact Table deletion query. */
        public static final String DELETE_CONTACT_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
