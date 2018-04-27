package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract;
import ca.bc.northvan.armintoussi.contactbook.Models.Address;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
import ca.bc.northvan.armintoussi.contactbook.R;

public class EditContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /** Debugging class tag. */
    private static final String TAG = EditContactActivity.class.getName();

    private static final int _ID      = 0;
    private static final int PERSON_ID = 1;
    private static final int ADDRESS_ID = 2;
    private static final int EMAIL    = 3;
    private static final int HOME_P   = 4;
    private static final int MOBILE_P = 5;
    private static final int F_NAME   = 7;
    private static final int M_NAME   = 8;
    private static final int L_NAME   = 9;
    private static final int STREET_ADDY = 11;
    private static final int CITY = 12;
    private static final int REGION = 13;
    private static final int COUNTRY = 14;
    private static final int POST_CODE = 15;


    private EditText mFName;
    private EditText mMName;
    private EditText mLName;
    private EditText mMNumber;
    private EditText mHNumber;
    private EditText mEmail;
    private EditText mStreetAddress;
    private EditText mCity;
    private EditText mRegion;
    private EditText mCountry;
    private EditText mPostCode;

    private Button mEdit;
    private Button mDel;

    private long id;

    private Contact mContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        handleBundle();
        getViewReferences();
        setBtnListeners();

        getLoaderManager().initLoader(0, null, this);

//        populateEditContactForm();
    }

    private void getViewReferences() {
        mFName         = findViewById(R.id.f_name);
        mMName         = findViewById(R.id.m_name);
        mLName         = findViewById(R.id.l_name);
        mMNumber       = findViewById(R.id.mobile_num);
        mHNumber       = findViewById(R.id.home_num);
        mEmail         = findViewById(R.id.email);
        mStreetAddress = findViewById(R.id.address);
        mCity          = findViewById(R.id.city);
        mRegion        = findViewById(R.id.region);
        mCountry       = findViewById(R.id.country);
        mPostCode      = findViewById(R.id.post_code);
        mEdit          = findViewById(R.id.edit);
        mDel           = findViewById(R.id.delete);
    }

    private void populateEditContactForm() {
        mFName.setText(mContact.getPerson().getFirstName());
    }

    private void handleBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("contact");
        Log.i(TAG, "Id: " + id);
    }


    private void setBtnListeners() {
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Edit clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Builds the contact from the cursor results of
     * the id that was passed from Home Activity.
     *
     * @param cursor a cursor with a single row.
     */
    private void buildContact(final Cursor cursor) {
        Person  person  = createPerson(cursor);
        Address address = createAddress(cursor);

        mContact = createContact(cursor, person, address);
    }

    /**
     * Creates a Contact from the cursor results.
     *
     * @param cursor a cursor with a single row.
     * @param person a person previously created.
     * @param address an address previously created.
     *
     * @return a built Contact with all fields.
     */
    private Contact createContact(final Cursor cursor, final Person person, final Address address) {
        cursor.moveToFirst();
        return Contact.ContactBuilder.createContact(cursor.getLong(_ID), address, person,
                                                    cursor.getString(EMAIL),
                                                    cursor.getString(HOME_P),
                                                    cursor.getString(MOBILE_P));
    }

    /**
     * Creates a Person from the cursor results.
     *
     * @param cursor a cursor with a single row.
     *
     * @return a built person with all fields.
     */
    private Person createPerson(Cursor cursor) {
        cursor.moveToFirst();
        return Person.PersonBuilder.createPerson(cursor.getString(F_NAME), cursor.getString(M_NAME), cursor.getString(L_NAME));
    }

    /**
     * Create an address from the cursor results.
     *
     * @param cursor a cursor with a single row.
     *
     * @return a built address with all fields.
     */
    private Address createAddress(Cursor cursor) {
        cursor.moveToFirst();
        return Address.AddressBuilder.createAddress(cursor.getString(STREET_ADDY),
                                                    cursor.getString(CITY),
                                                    cursor.getString(REGION),
                                                    cursor.getString(COUNTRY),
                                                    cursor.getString(POST_CODE));
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader loader;
        final Uri uri = ContactBookDatabaseContract.ContactTable.CONTACT_FULL_URI;

        loader = new CursorLoader(EditContactActivity.this,
                 uri.withAppendedPath(uri, "" + this.id), null, null,
                                                                   null, null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        buildContact(data);
        populateEditContactForm();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //stuff.
    }
}
