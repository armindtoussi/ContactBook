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
 * A Camera utility class to dispatch a Camera intent,
 * create a file, save, and pass the uri back to caller.
 *
 */
public class CameraUtil {
    /** Debug class tag. */
    private static final String TAG = "CameraUtil";

    /** OnActivityResult request code. */
    private static final int REQUEST_CAMERA_CAPTURE = 1;

    /** Calling activity. */
    private Activity mActivity;
    /** The path where the image is located. */
    private String mImagePath;

    /**
     * ctor takes the calling activity and creates an obj of this.
     *
     * @param activity the calling activity.
     */
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

    /**
     * Gets the image path.
     *
     * @return the image path as a String.
     */
    public String getImagePath() {
        return mImagePath;
    }
}
