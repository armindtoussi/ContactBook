package ca.bc.northvan.armintoussi.contactbook.Utilities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by armin2 on 4/14/2018.
 */

public class CameraUtil {
    private static final String TAG = "CameraUtil";

    private static final int REQUEST_CAMERA_CAPTURE = 1;

    private Activity mActivity;

    private String mImagePath;

    public CameraUtil(Activity activity) {
        this.mActivity = activity;
    }


    /**
     * create a file for the image.
     *
     * @return the file path as a string.
     * @throws IOException on file creation.
     */
    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = mActivity.getBaseContext()
                                   .getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",    /* suffix */
                storageDir      /* directory */
        );

        mImagePath = image.getAbsolutePath();
        return image;
    }

    /**
     * Dispatches intent to default camera app on phone for
     * taking an image.
     */
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(mActivity.getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(mActivity.getBaseContext(),
                        "ca.bc.northvan.armintoussi.contactbook.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                mActivity.startActivityForResult(takePictureIntent, REQUEST_CAMERA_CAPTURE);
            }
        }
    }

    public String getImagePath() {
        return mImagePath;
    }
}
