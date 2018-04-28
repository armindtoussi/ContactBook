package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.app.LoaderManager;
import android.content.ContentValues;
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

import ca.bc.northvan.armintoussi.contactbook.Models.Address;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
import ca.bc.northvan.armintoussi.contactbook.R;
import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

import static ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract.*;

/**
 * Created By Armin Toussi: 4/26/2018
 *
 * Edit Contact activity. It allows the user to edit the
 * contact's information or delete the contact entirely.
 *
 */
public class EditContactActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /** Debugging class tag. */
    private static final String TAG = EditContactActivity.class.getName();

    /** The Contact _ID column code. */
    private static final int _ID      = 0;
    /** The Person _ID column code. */
    private static final int PERSON_ID = 1;
    /** The Address _ID column code. */
    private static final int ADDRESS_ID = 2;
    /** The Contact Email column code. */
    private static final int EMAIL    = 3;
    /** The Contact Home Phone column code. */
    private static final int HOME_P   = 4;
    /** The Contact Mobile Phone column code. */
    private static final int MOBILE_P = 5;
    /** The Contact First Name column code. */
    private static final int F_NAME   = 7;
    /** The Contact Last Name column code. */
    private static final int L_NAME   = 8;
    /** The Contact Middle Name column code. */
    private static final int M_NAME   = 9;
    /** The Contact Street Address column code. */
    private static final int STREET_ADDY = 11;
    /** The Contact City column code. */
    private static final int CITY = 12;
    /** The Contact Region column code. */
    private static final int REGION = 13;
    /** The Contact Country column code. */
    private static final int COUNTRY = 14;
    /** The Contact Postal Code column code. */
    private static final int POST_CODE = 15;

    /** First Name Edit Text. */
    private EditText mFName;
    /** Middle Name Edit Text. */
    private EditText mMName;
    /** Last Name Edit Text. */
    private EditText mLName;
    /** Mobile Number Edit Text. */
    private EditText mMNumber;
    /** Home Number Edit Text. */
    private EditText mHNumber;
    /** Email Edit Text. */
    private EditText mEmail;
    /** Street Address Edit Text. */
    private EditText mStreetAddress;
    /** City Edit Text. */
    private EditText mCity;
    /** Region Edit Text. */
    private EditText mRegion;
    /** Country Edit Text. */
    private EditText mCountry;
    /** Post Code Edit Text. */
    private EditText mPostCode;

    /** First name as a String. */
    private String first;
    /** Middle name as a String. */
    private String middle;
    /** Last name as a String. */
    private String last;
    /** Home phone # as a String. */
    private String home;
    /** Mobile phone # as a String. */
    private String mobile;
    /** Email as a String. */
    private String email;
    /** Street Address as a String. */
    private String address;
    /** City as a String. */
    private String city;
    /** Region/state as a String. */
    private String region;
    /** Country as a String. */
    private String country;
    /** Post Code as a String. */
    private String postCode;

    /** Edit Contact Button. */
    private Button mEdit;
    /** Delete Contact Button. */
    private Button mDel;

    /** Contact _ID of the selected contact. */
    private long id;
    /** The selected Constructed Contact. */
    private Contact mContact;

    /**
     * onCreate method, initiates and inflates the view.
     * Also handles some startup needs like adapters and
     * getting references to views.
     *
     * @param savedInstanceState previous state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        handleBundle();
        getViewReferences();
        setBtnListeners();

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Gets all layout view references.
     */
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

    /**
     * Populates the edit text views with all the
     * contact information.
     *
     */
    private void populateEditContactForm() {
        mFName.setText(mContact.getPerson().getFirstName());
        if(Utilities.checkNotNullNotEmpty(mContact.getPerson().getMiddleName())) {
            mMName.setText(mContact.getPerson().getMiddleName());
        }
        mLName.setText(mContact.getPerson().getLastName());
        mHNumber.setText(mContact.getHomePhoneNumber());
        mMNumber.setText(mContact.getMobilePhoneNumber());
        mEmail.setText(mContact.getEmail());
        if(Utilities.checkNotNullNotEmpty(mContact.getAddress().getAddrStreetAddress())) {
            mStreetAddress.setText(mContact.getAddress().getAddrStreetAddress());
        }
        if(Utilities.checkNotNullNotEmpty(mContact.getAddress().getAddrCity())) {
            mCity.setText(mContact.getAddress().getAddrCity());
        }
        if(Utilities.checkNotNullNotEmpty(mContact.getAddress().getAddrState())) {
            mRegion.setText(mContact.getAddress().getAddrState());
        }
        if(Utilities.checkNotNullNotEmpty(mContact.getAddress().getAddrCountry())) {
            mCountry.setText(mContact.getAddress().getAddrCountry());
        }
        if(Utilities.checkNotNullNotEmpty(mContact.getAddress().getAddrPostCode())) {
            mPostCode.setText(mContact.getAddress().getAddrPostCode());
        }
    }

    /**
     * Handles the incoming bundle,
     * gets the Contact _ID.
     */
    private void handleBundle() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        id = bundle.getLong("contact");
    }

    /**
     * Validates certain fields in the contact form.
     *
     * @return true if all tests pass.
     */
    private boolean validateContactInformation() {
        if(!Utilities.checkNotNullNotEmpty(mFName.getText().toString())) {
            mFName.setError(getResources().getString(R.string.name_error));
            return false;
        }
        if(!Utilities.checkNotNullNotEmpty(mLName.getText().toString())) {
            mLName.setError(getResources().getString(R.string.name_error));
            return false;
        }
        if(!Utilities.checkNotNullNotEmpty(mMNumber.getText().toString())) {
            mMNumber.setError(getResources().getString(R.string.phone_num_error));
            return false;
        }
        if(Utilities.checkNotNullNotEmpty(mEmail.getText().toString())) {
            if(!Utilities.checkEmailFormat(mEmail.getText().toString().toCharArray())) {
                mEmail.setError(getResources().getString(R.string.email_error));
                return false;
            }
        }
        return true;
    }

    /**
     * Grabs the text from the edit and puts
     * it into a hash map for updating.
     */
    private void getEditedContactFromView() {
        first    = mFName.getText().toString();
        middle   = mMName.getText().toString();
        last     = mLName.getText().toString();
        home     = mHNumber.getText().toString();
        mobile   = mMNumber.getText().toString();
        email    = mEmail.getText().toString();
        address  = mStreetAddress.getText().toString();
        city     = mCity.getText().toString();
        region   = mRegion.getText().toString();
        country  = mCountry.getText().toString();
        postCode = mPostCode.getText().toString();
    }

    /**
     * Initiates the necessary methods for updating
     * a contact in full including address and person.
     */
    private void initUpdateEditedContact() {
        updatePerson();
        updateAddress();
        updateContact();
    }

    /**
     * Updates a single Person.
     *
     * @return the number of rows affected as an int.
     */
    private int updatePerson() {
        ContentValues values = new ContentValues();
        values.put(PersonTable.F_NAME, first);
        values.put(PersonTable.M_NAME, middle);
        values.put(PersonTable.L_NAME, last);

        return getContentResolver().update(Uri.withAppendedPath(
                PersonTable.PERSON_ITEM_URI, "" + mContact.getPerson_id()),
                         values, "" + mContact.getPerson_id(), null);
    }

    /**
     * Updates a single Address.
     *
     * @return the number of rows affected as an int.
     */
    private int updateAddress() {
        ContentValues values = new ContentValues();
        values.put(AddressTable.STREET_ADDR, address);
        values.put(AddressTable.CITY_ADDR, city);
        values.put(AddressTable.STATE_ADDR, region);
        values.put(AddressTable.COUNTRY_ADDR, country);
        values.put(AddressTable.POST_ADDR, postCode);

        return getContentResolver().update(Uri.withAppendedPath(
                AddressTable.ADDRESS_ITEM_URI, "" + mContact.getAddress_id()),
                values, "" + mContact.getAddress_id(), null);
    }

    /**
     * Updates a single contact.
     *
     * @return the number of rows affected as an int.
     */
    private int updateContact() {
        ContentValues values = new ContentValues();
        values.put(ContactTable.PERSON_ID, mContact.getPerson_id());
        values.put(ContactTable.ADDRESS_ID, mContact.getAddress_id());
        values.put(ContactTable.EMAIL, email);
        values.put(ContactTable.MOBILE_PHONE, mobile);
        values.put(ContactTable.HOME_PHONE, home);

        return getContentResolver().update(Uri.withAppendedPath(
                ContactTable.CONTACT_ITEM_URI, "" + this.id),
                values, "" + this.id, null);
    }

    /**
     * Sets the button listeners for the
     * edit and delete contact buttons.
     */
    private void setBtnListeners() {
        mEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateContactInformation()) {
                    //prepare edited info.
                    getEditedContactFromView();
                    initUpdateEditedContact();

                    //send things back cuz we donee.
                    Intent results = new Intent();
                    results.putExtra("id", (int)id + 20);
                    setResult(RESULT_OK, results);
                    finish();
                }
            }
        });

        mDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Delete clicked", Toast.LENGTH_SHORT).show();
                getContentResolver().delete(Uri.withAppendedPath(
                        ContactTable.CONTACT_ITEM_URI, "" + id),
                        "" + id, null);

                Intent results = new Intent();
                results.putExtra("id", (int)id + 20);
                setResult(RESULT_OK, results);
                finish();

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
        Log.i(TAG, "create Contact PERSON ID: " + cursor.getLong(PERSON_ID));
        return Contact.ContactBuilder.createContact(cursor.getLong(_ID),
                                                    cursor.getLong(PERSON_ID),
                                                    cursor.getLong(ADDRESS_ID),
                                                    address, person,
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
        return Person.PersonBuilder.createPerson(cursor.getString(F_NAME),
                                                 cursor.getString(M_NAME),
                                                 cursor.getString(L_NAME));
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

    /**
     * When loader is initiated, this function is called.
     * Makes a query to the provider for a single row from
     * the Contact Table.
     *
     * @param id the id of the loader.
     * @param args additional arguments in a Bundle.
     *
     * @return a cursor loader.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader loader;
        final Uri uri = ContactTable.CONTACT_ITEM_URI;

        loader = new CursorLoader(EditContactActivity.this,
                 uri.withAppendedPath(uri, "" + this.id), null, null,
                                                                   null, null);
        return loader;
    }

    /**
     * When load is finished, this method is called. Should have results
     * of a successful query.
     *
     * @param loader the completely cursor loaded.
     * @param data a Cursor with results of the query.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        buildContact(data);
        populateEditContactForm();
    }

    /**
     * When loader resets this is called.
     * @param loader the loader.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        //stuff.
    }
}
