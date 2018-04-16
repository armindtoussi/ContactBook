package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.bc.northvan.armintoussi.contactbook.Models.Address;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
import ca.bc.northvan.armintoussi.contactbook.R;
import ca.bc.northvan.armintoussi.contactbook.Utilities.CameraUtil;


public class CreateContactActivity extends AppCompatActivity {
    private static final String TAG = "CreateContactActivity";

    private static final int REQUEST_CAMERA_CAPTURE        = 1;
    private static final int REQUEST_CAM_WRITE_PERMISSIONS = 2;
    private static final int REQUEST_PICK_IMAGE            = 3;

    private SimpleDraweeView mImage;

    private ImageView mTakePicture;
    private ImageView mChoosePicture;

    private String mImagePath;

    private EditText mFirst;
    private EditText mMiddle;
    private EditText mLast;
    private EditText mMobilePhone;
    private EditText mHomePhone;
    private EditText mEmail;
    private EditText mAddressOne;
    private EditText mAddressTwo;
    private EditText mCity;
    private EditText mPostCode;
    private EditText mRegion;
    private EditText mCountry;

    private Button mAddContactBtn;

    private String first;
    private String middle;
    private String last;
    private String mobile;
    private String home;
    private String email;
    private String address;
    private String city;
    private String postCode;
    private String region;
    private String country;

    private boolean hasMiddleName;
    private boolean hasHomePhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact);

        this.getReferences();
        this.setListeners();

        //call permissions after view inflated.
        getCameraAndStoragePermissions();
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
        if(!checkNotNullNotEmpty(first)) {
            mFirst.setError(getResources().getString(R.string.name_error));
            return false;
        }

        if(checkNotNullNotEmpty(middle)) {
            hasMiddleName = true;
        }

        if(!checkNotNullNotEmpty(last)) {
            mLast.setError(getResources().getString(R.string.name_error));
            return false;
        }

        if(!checkNotNullNotEmpty(mobile)) {
            mMobilePhone.setError(getResources().getString(R.string.phone_num_error));
            return false;
        }

        if(!checkNotNullNotEmpty(home)) {
            hasHomePhone = true;
        }

        if(checkNotNullNotEmpty(email)) {
            if(!checkEmailFormat(email.toCharArray())) {
                mEmail.setError(getResources().getString(R.string.email_error));
                return false;
            }
        }
        return true;
    }

    /**
     * Check if address line is empty.
     *
     * @return true if not empty.
     */
    private boolean checkForAddress() {
        if(!checkNotNullNotEmpty(address)) {
            return false;
        }
        return true;
    }

    /**
     * Checks that strings aren't null or empty.
     *
     * @param isNull arg to check.
     * @return true if not null or not empty.
     */
    private boolean checkNotNullNotEmpty(String isNull) {
        if(isNull == null || isNull.length() < 1) {
            return false;
        }
        return true;
    }

    /**
     * Check that the phone number is formatted the way we want.
     * formatting: ###-###-####
     *
     * @param number the number to format.
     * @return constructed & built string.
     */
    private String checkPhoneNumberFormat(char[] number) {
        if(number.length == 10) {
            return this.insertPhoneHyphens(number);
        }//otherwise it's 12 just return.
        return number.toString();
    }

    /**
     * Inserts the hypens into a phone number for
     * consistent formatting.
     *
     * @param number the number to format.
     * @return constructed & formatted string.
     */
    private String insertPhoneHyphens(char[] number) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < number.length; i++) {
            if(i == 2 || i == 5) {
                sb.append(number[i]);
                sb.append("-");
            } else {
                sb.append(number[i]);
            }
        }
        return sb.toString();
    }

    /**
     * Checks if the email has an @ symbol.
     *
     * @param email the email to check.
     * @return true if has @ symbol.
     */
    private boolean checkEmailFormat(char[] email) {
        for(char c: email) {
            if(c == '@') {
                return true;
            }
        }
        return false;
    }

    /**
     * Creates a person using person builder.
     *
     * @return a contructed person w/wo middle name.
     */
    private Person createPerson() {
        if(hasMiddleName) {
            return new Person.Builder().firstName(first).lastName(last).middleName(middle).build();
        } else {
            return new Person.Builder().firstName(first).lastName(last).build();
        }
    }

    /**
     * creates an address with all the valid fields.
     *
     * @return a build Address obj.
     */
    private Address createAddress() {
        Address.Builder pb = new Address.Builder();

        if(checkNotNullNotEmpty(address)) {
            pb.addrStreetAddress(address);
        }
        if(checkNotNullNotEmpty(city)) {
            pb.addrCity(city);
        }
        if(checkNotNullNotEmpty(region)) {
            pb.addrState(region);
        }
        if(checkNotNullNotEmpty(country)) {
            pb.addrCountry(country);
        }
        if(checkNotNullNotEmpty(postCode)) {
            pb.addrPostCode(postCode);
        }
        return pb.build();
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
        if(home != null) {
            cb.homePhoneNumber(checkPhoneNumberFormat(home.toCharArray()));
        }
        if(mobile != null) {
            cb.mobilePhoneNumber(checkPhoneNumberFormat(mobile.toCharArray()));
        }
        if(mImagePath != null) {
            cb.image(Uri.parse(mImagePath));
        }
        return cb.build();
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
                } else {
                    //take picture
                    CameraUtil cu = new CameraUtil(CreateContactActivity.this);
                    cu.dispatchTakePictureIntent();
                    mImagePath = cu.getImagePath();
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

    private void btnListener() {
        mAddContactBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getContactInformation();
                if(validateContactInformation()) {
                    //do stuff with the contact.
                    Person person  = createPerson();
                    Address address = null;
                    if(checkForAddress()) {
                        //create the address.
                        address = createAddress();
                    }

                    Contact contact = createContact(address, person);
                    //left in for now.
                    Log.i(TAG, contact.getEmail());
                    Log.i(TAG, contact.getPerson().getFirstName());
                    Log.i(TAG, contact.getMobilePhoneNumber());
                    Log.i(TAG, contact.getAddress().getAddrStreetAddress());
                    Log.i(TAG, contact.getAddress().getAddrCountry());
                }
            }
        });
    }
}
