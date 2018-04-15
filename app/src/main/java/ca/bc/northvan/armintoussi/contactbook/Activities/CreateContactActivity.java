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
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;

import java.io.FileNotFoundException;
import java.io.InputStream;

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
        mTakePicture      = findViewById(R.id.take_pic);
        mChoosePicture    = findViewById(R.id.choose_pic);
        mImage            = findViewById(R.id.contact_img);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CAMERA_CAPTURE && resultCode == RESULT_OK) {
            Uri uri = Uri.parse(mImagePath);
            mImage.setImageURI(uri);
        } else if (requestCode == REQUEST_CAM_WRITE_PERMISSIONS && resultCode == RESULT_OK) {
            mTakePicture.setEnabled(true);
        } else if (requestCode == REQUEST_PICK_IMAGE && resultCode == RESULT_OK) {
            Log.i(TAG, "Data: " + data);
            Uri uri = data.getData();
            mImage.setImageURI(uri);
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
    }

    /**
     * sets the picture listeners.
     */
    private void pictureListeners() {
        mTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkForCamera()) {
                    Toast.makeText(CreateContactActivity.this, "Your phone does not have a camera, sorry.", Toast.LENGTH_SHORT).show();
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
}
