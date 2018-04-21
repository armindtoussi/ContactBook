package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseHelper;
import ca.bc.northvan.armintoussi.contactbook.R;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton mFloatingAddContact;

    private Toolbar mToolbar;

    ContactBookDatabaseHelper helper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        helper = ContactBookDatabaseHelper.getInstance(getApplicationContext());

        getViewReferences();
        setBtnListeners();
        setSupportActionBar(mToolbar);
    }

    /**
     * Gets view references.
     */
    private void getViewReferences() {
        mFloatingAddContact = findViewById(R.id.floatingAddContactBtn);
        mToolbar            = findViewById(R.id.toolbar);
    }

    /**
     * Sets button listeners for this activity.
     */
    private void setBtnListeners() {
        mFloatingAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, CreateContactActivity.class));
            }
        });
    }
}
