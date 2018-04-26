package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.suitebuilder.annotation.Suppress;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseHelper;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactContentProvider;
import ca.bc.northvan.armintoussi.contactbook.Models.Address;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
import ca.bc.northvan.armintoussi.contactbook.R;
import ca.bc.northvan.armintoussi.contactbook.Utilities.CameraUtil;
import ca.bc.northvan.armintoussi.contactbook.Utilities.Utilities;

import static java.lang.Long.parseLong;

/**
 * Created by armin2 on 4/18/2018.
 *
 * Activity for adding contacts to your list.
 */
public class CreateContactActivity extends AppCompatActivity {
    /** Debugging class tag. */
    private static final String TAG = "CreateContactActivity";

    /** On Activity Request intent code for camera capture. */
    private static final int REQUEST_CAMERA_CAPTURE        = 1;
    /** On Activity Request intent code for camera write. */
    private static final int REQUEST_CAM_WRITE_PERMISSIONS = 2;
    /** On Activity Request intent code for picking image. */
    private static final int REQUEST_PICK_IMAGE            = 3;

    /** Fresco Drawee view for display selected image. */
    private SimpleDraweeView mImage;

    /** Take picture button. */
    private ImageView mTakePicture;
    /** Choose picture button. */
    private ImageView mChoosePicture;

    /** The image path on phone, to be converted to uri. */
    private String mImagePath;

    /** EditText for first name. */
    private EditText mFirst;
    /** EditText for middle name. */
    private EditText mMiddle;
    /** EditText for last name. */
    private EditText mLast;
    /** EditText for mobile phone. */
    private EditText mMobilePhone;
    /** EditText for home phone. */
    private EditText mHomePhone;
    /** EditText for email. */
    private EditText mEmail;
    /** EditText for address line 1. */
    private EditText mAddressOne;
    /** EditText for address line 2. */
    private EditText mAddressTwo;
    /** EditText for city. */
    private EditText mCity;
    /** EditText for post code. */
    private EditText mPostCode;
    /** EditText for region/state/province. */
    private EditText mRegion;
    /** EditText for country. */
    private EditText mCountry;

    /** Add contact button. */
    private Button mAddContactBtn;

    /** String that holds first name. */
    private String first;
    /** String that holds middle name. */
    private String middle;
    /** String that holds last name. */
    private String last;
    /** String that holds mobile phone. */
    private String mobile;
    /** String that holds home phone. */
    private String home;
    /** String that holds email address. */
    private String email;
    /** String that holds street address. */
    private String address;
    /** String that holds city. */
    private String city;
    /** String that holds post code. */
    private String postCode;
    /** String that holds region/state/prov. */
    private String region;
    /** String that holds country. */
    private String country;

    /** Boolean for checking if user enter a home phone. */
    private boolean hasHomePhone;

    /** todo - temp helper for inserting contacts. will be changed to a provider. */
    private ContactBookDatabaseHelper contactHelper;
    private ContactContentProvider mContentProvider;

    /**
     * onCreate method, initiates and inflates the view.
     * Also handles some startup needs like permissions and
     * getting references to views.
     *
     * @param savedInstanceState previous instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        this.getReferences();
        this.setListeners();

        //call permissions after view inflated.
        getCameraAndStoragePermissions();

        contactHelper = ContactBookDatabaseHelper.getInstance(getApplicationContext());
        mContentProvider = new ContactContentProvider();

        //TODO - remove this block of code it's testing.
        SQLiteDatabase db;
        db = contactHelper.getWritableDatabase();
        Log.i(TAG, "nuM ENTRIES: " + contactHelper.getNumberOfContacts(db));
        //todo - end of block of code to remove.
    }

    /**
     * Get view references.
     */
    private void getReferences() {
        mTakePicture   = findViewById(R.id.take_pic);
        mChoosePicture = findViewById(R.id.choose_pic);
        mImage         = findViewById(R.id.contact_img);
        mFirst         = findViewById(R.id.first_name);
        mMiddle        = findViewById(R.id.middle_name);
        mLast          = findViewById(R.id.last_name);
        mMobilePhone   = findViewById(R.id.phone_mobile);
        mHomePhone     = findViewById(R.id.phone_home);
        mEmail         = findViewById(R.id.email);
        mAddressOne    = findViewById(R.id.street_address_one);
        mAddressTwo    = findViewById(R.id.street_address_two);
        mCity          = findViewById(R.id.city);
        mPostCode      = findViewById(R.id.post_code);
        mRegion        = findViewById(R.id.region);
        mCountry       = findViewById(R.id.country);
        mAddContactBtn = findViewById(R.id.add_contact_btn);
    }

    /**
     * hands callbacks from intent calls with results expected.
     *
     * @param requestCode request code of the intent.
     * @param resultCode the result of the intent.
     * @param data the data shipped back by the intent.
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            Uri uri = Uri.parse(mImagePath);
            mImage.setImageURI(uri);
        } else if (requestCode == REQUEST_CAM_WRITE_PERMISSIONS && resultCode == RESULT_OK) {
            mTakePicture.setEnabled(true);
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            mImage.setImageURI(uri);
        }
    }

    /**
     * Gets all text from the contact form.
     */
    private void getContactInformation() {
        first    = mFirst.getText().toString();
        middle   = mMiddle.getText().toString();
        last     = mLast.getText().toString();
        mobile   = mMobilePhone.getText().toString();
        home     = mHomePhone.getText().toString();
        email    = mEmail.getText().toString();
        address  = mAddressOne.getText().toString() + " " + mAddressTwo.getText().toString();
        city     = mCity.getText().toString();
        postCode = mPostCode.getText().toString();
        region   = mRegion.getText().toString();
        country  = mCountry.getText().toString();
    }

    /**
     * Validates certain fields in the contact form.
     *
     * @return true if all tests pass.
     */
    private boolean validateContactInformation() {
        if(!Utilities.checkNotNullNotEmpty(first)) {
            mFirst.setError(getResources().getString(R.string.name_error));
            return false;
        }

        if(!Utilities.checkNotNullNotEmpty(last)) {
            mLast.setError(getResources().getString(R.string.name_error));
            return false;
        }

        if(!Utilities.checkNotNullNotEmpty(mobile)) {
            mMobilePhone.setError(getResources().getString(R.string.phone_num_error));
            return false;
        }

        if(Utilities.checkNotNullNotEmpty(home)) {
            hasHomePhone = true;
        }

        if(Utilities.checkNotNullNotEmpty(email)) {
            if(!Utilities.checkEmailFormat(email.toCharArray())) {
                mEmail.setError(getResources().getString(R.string.email_error));
                return false;
            }
        }
        return true;
    }

    /**
     * Creates a person using person builder.
     *
     * @return a contructed person w/wo middle name.
     */
    private Person createPerson() {
        return Person.PersonBuilder.createPerson(first, last, middle);
    }

    /**
     * creates an address with all the valid fields.
     *
     * @return a build Address obj.
     */
    private Address createAddress() {
        return Address.AddressBuilder.createAddress(address, city, region, country, postCode);
    }

    /**
     * Creates the contact from all gathered information.
     *
     * @param address the address to add to the contact.
     * @param person the person to add to the contact.
     *
     * @return a constructed contact.
     */
    private Contact createContact(Address address, Person person) {
        Contact.Builder cb = new Contact.Builder();

        if(person != null) {
            cb.person(person);
        }
        if(address != null) {
            cb.address(address);
        }
        if(email != null) {
            cb.email(email);
        }
        if(hasHomePhone) {
            cb.homePhoneNumber(Utilities.checkPhoneNumberFormat(home.toCharArray()));
        }
        if(mobile != null) {
            cb.mobilePhoneNumber(Utilities.checkPhoneNumberFormat(mobile.toCharArray()));
        }
        if(mImagePath != null) {
            cb.image(Uri.parse(mImagePath));
        }
        return cb.build();
    }

    /**
     * Inserts a person into the database using the content provider.
     *
     * @param contact the contact obj with the Person to insert.
     *
     * @return the _id of the Person inserted as a long.
     *
     * @throws IllegalArgumentException if the Uri is incorrect.
     */
    private long insertPerson(final Contact contact) throws IllegalArgumentException {
        ContentValues cv = new ContentValues();

        cv.put(ContactBookDatabaseContract.PersonTable.F_NAME, contact.getPerson().getFirstName());
        cv.put(ContactBookDatabaseContract.PersonTable.L_NAME, contact.getPerson().getLastName());
        cv.put(ContactBookDatabaseContract.PersonTable.M_NAME, contact.getPerson().getLastName());

        Uri uri = mContentProvider.insert(ContactBookDatabaseContract.PersonTable.PERSON_CONTENT_URI, cv);
        Log.i(TAG, "Persy URI: " + uri.toString());
        return parseLong(uri.getLastPathSegment());
    }

    /**
     * Inserts an Address into the database using the content provider.
     *
     * @param contact the contact obj with the Address to insert.
     *
     * @return the _id of the Address inserted as a long.
     *
     * @throws IllegalArgumentException if the Uri is incorrect.
     */
    private long insertAddress(final Contact contact) throws IllegalArgumentException {
        ContentValues cv = new ContentValues();

        cv.put(ContactBookDatabaseContract.AddressTable.STREET_ADDR, contact.getAddress().getAddrStreetAddress());
        cv.put(ContactBookDatabaseContract.AddressTable.CITY_ADDR, contact.getAddress().getAddrCity());
        cv.put(ContactBookDatabaseContract.AddressTable.STATE_ADDR, contact.getAddress().getAddrState());
        cv.put(ContactBookDatabaseContract.AddressTable.COUNTRY_ADDR, contact.getAddress().getAddrCountry());
        cv.put(ContactBookDatabaseContract.AddressTable.POST_ADDR, contact.getAddress().getAddrPostCode());

        Uri uri = mContentProvider.insert(ContactBookDatabaseContract.AddressTable.ADDRESS_CONTENT_URI, cv);
        return parseLong(uri.getLastPathSegment());
    }

    /**
     * Inserts a Contact into the database using the content provider.
     *
     * @param contact the contact obj to insert.
     * @param pid the person  _id.
     * @param aid the address _id.
     *
     * @throws IllegalArgumentException if the Uri is incorrect.
     */
    private void insertContact(final Contact contact, final long pid, final long aid)
                                                    throws IllegalArgumentException {
        ContentValues cv = new ContentValues();

        cv.put(ContactBookDatabaseContract.ContactTable.ADDRESS_ID, aid);
        cv.put(ContactBookDatabaseContract.ContactTable.PERSON_ID, pid);
        cv.put(ContactBookDatabaseContract.ContactTable.HOME_PHONE, contact.getHomePhoneNumber());
        cv.put(ContactBookDatabaseContract.ContactTable.MOBILE_PHONE, contact.getMobilePhoneNumber());
        cv.put(ContactBookDatabaseContract.ContactTable.EMAIL, contact.getEmail());

        Uri uri = mContentProvider.insert(ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_URI, cv);
    }

    /**
     * Performs the operations around inserting a contact into the database.
     * Inserts the person,  reserves the _id.
     * Inserts the address, reserves the _id.
     * Finally it in inserts the contact using the reserved _id's
     *
     * @param contact the contact to insert.
     */
    private void performContactInsertionOperation(final Contact contact) {
        try {
            long pid = insertPerson(contact);
            long aid = insertAddress(contact);
            insertContact(contact, pid, aid);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Toast.makeText(CreateContactActivity.this,
                    "Failed to add a contact.",  Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(CreateContactActivity.this,
                    "Failed to add a contact.",  Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Dispatches intent to select a local picture.
     */
    private void selectPicture() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_PICK_IMAGE);
    }

    /**
     * Checks for camera on this user phone.
     *
     * @return true if camera available.
     */
    private boolean checkForCamera() {
        final PackageManager pm = this.getPackageManager();
        if(pm.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            return true;
        }
        return false;
    }

    /**
     * Checks for cam and storage permissions.
     */
    private void getCameraAndStoragePermissions() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            mTakePicture.setEnabled(false);

            ActivityCompat.requestPermissions(this, new String[] {
                    Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, REQUEST_CAM_WRITE_PERMISSIONS);
        }
    }

    /**
     * sets all listeners.
     */
    private void setListeners() {
        this.pictureListeners();
        this.btnListener();
    }

    /**
     * sets the picture listeners.
     */
    private void pictureListeners() {
        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkForCamera()) {
                    Toast.makeText(CreateContactActivity.this,
                            getResources().getString(R.string.no_camera_warn),
                            Toast.LENGTH_SHORT).show();
                } else { //todo - chage the entire way we're doing pics.
                    //take picture
                    CameraUtil cu = new CameraUtil(CreateContactActivity.this);
                    cu.dispatchTakePictureIntent();
                    mImagePath = cu.getImagePath();
                    //todo - remove.
                    Log.i(TAG, "image path: " + mImagePath);
                }
            }
        });

        mChoosePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPicture();
            }
        });
    }

    /**
     * Set the button listeners.
     */
    private void btnListener() {
        mAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContactInformation();
                if(validateContactInformation()) {
                    //do stuff with the contact.
                    Person person   = createPerson();
                    Address address = createAddress();
                    Contact contact = createContact(address, person);
                    //todo - remove - left in for now.
                    Log.i(TAG, contact.getEmail());
                    Log.i(TAG, contact.getPerson().getFirstName());
                    Log.i(TAG, contact.getMobilePhoneNumber());
                    Log.i(TAG, contact.getHomePhoneNumber());
                    //todo - part of the remove segment.

                    performContactInsertionOperation(contact);
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
    }
}
