package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.facebook.drawee.backends.pipeline.Fresco;

import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseHelper;

/**
 * Load up activity, just has a splash page then
 * pushes to the home activity when loading is complete.
 * todo - this is where i should load contacts from db and then push to home activity.
 */
public class SplashActivity extends AppCompatActivity {

    /**
     * onCreate method, initiates and inflates the view.
     * Pushes to HomeActivity when load is complete.
     *
     * @param savedInstanceState previous instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fresco.initialize(this);

        startActivity(new Intent(SplashActivity.this, HomeActivity.class));

        finish();
    }
}
