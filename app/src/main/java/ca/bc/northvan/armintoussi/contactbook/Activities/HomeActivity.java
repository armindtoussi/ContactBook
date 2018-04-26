package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.bc.northvan.armintoussi.contactbook.Adapters.ContactBookRecyclerAdapter;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract;
import ca.bc.northvan.armintoussi.contactbook.R;

/**
 * Created by armin2 on 4/18/2018.
 *
 * Main activity of the application. It holds the recycler view
 * that displays all contacts.
 *
 */
public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    /** Debugging class tag. */
    private static final String TAG = HomeActivity.class.getName();

    /** Email Column on person & contact table join. */
    public static final int EMAIL_COL  = 3;
    /** Home phone Column on person & contact table join. */
    public static final int HOME_COL   = 4;
    /** Mobile phone Column on person & contact table join. */
    public static final int MOBILE_COL = 4;
    /** First name Column on person & contact table join. */
    public static final int F_NAME_COL = 7;
    /** Last name Column on person & contact table join. */
    public static final int M_NAME_COL = 8;
    /** Middle name Column on person & contact table join. */
    public static final int L_NAME_COL = 9;

    /** Floating Action Button for adding contact. */
    private FloatingActionButton mFloatingAddContact;
    /** Tool bar. */
    private Toolbar mToolbar;
    /** Recycler view that holds the view holders/contacts. */
    private RecyclerView mContactRecycler;
    /** Recycler adapter that populates the recycler. */
    private ContactBookRecyclerAdapter mAdapter;
    /** Layout manager that manages the recycler layout. */
    private RecyclerView.LayoutManager mRecyclerLayoutManager;


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
        setContentView(R.layout.activity_home);

        getViewReferences();
        setBtnListeners();
        setSupportActionBar(mToolbar);

        setupRecycler();
    }

    private void setupRecycler() {
        mContactRecycler.setHasFixedSize(true);
        mRecyclerLayoutManager = new LinearLayoutManager(this);
        mContactRecycler.setLayoutManager(mRecyclerLayoutManager);

        getLoaderManager().initLoader(0, null, this);
    }

    /**
     * Gets view references.
     */
    private void getViewReferences() {
        mFloatingAddContact = findViewById(R.id.floatingAddContactBtn);
        mToolbar            = findViewById(R.id.toolbar);
        mContactRecycler    = findViewById(R.id.contact_recycler);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == 1 && resultCode == RESULT_OK) {
            getLoaderManager().initLoader(1, null, this);

        }
    }

    /**
     * Sets button listeners for this activity.
     */
    private void setBtnListeners() {
        mFloatingAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(HomeActivity.this, CreateContactActivity.class), 1);
            }
        });
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        final CursorLoader loader;
        final Uri uri;

        uri    = ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_URI;
        loader = new CursorLoader(HomeActivity.this, uri, null, null, null,
                ContactBookDatabaseContract.PersonTable.F_NAME + " ASC");
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(mAdapter == null) {
            mAdapter = new ContactBookRecyclerAdapter(this, data, mContactRecycler);
            mContactRecycler.setAdapter(mAdapter);
        } else {
            mAdapter.swapCursor(data);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}
