package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.app.LoaderManager;
import android.content.ContentResolver;
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

import java.util.ArrayList;

import ca.bc.northvan.armintoussi.contactbook.Adapters.ContactRecyclerAdapter;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseHelper;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactContentProvider;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
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
    private ContactRecyclerAdapter mRecyclerAdapter;
    /** Layout manager that manages the recycler layout. */
    private RecyclerView.LayoutManager mRecyclerLayoutManager;

    /** Database helper. TODO - check later if this is needed. */
    private ContactBookDatabaseHelper mHelper;
    /** Content provider for accessing data. */
    private ContactContentProvider mProvider;

    private LoaderManager mManager;

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
        //todo - remove later.
        mHelper = ContactBookDatabaseHelper.getInstance(getApplicationContext());

        getViewReferences();
        setBtnListeners();
        setSupportActionBar(mToolbar);

        mContactRecycler.setHasFixedSize(true);
        mRecyclerLayoutManager = new LinearLayoutManager(this);
        mContactRecycler.setLayoutManager(mRecyclerLayoutManager);

        mRecyclerAdapter = new ContactRecyclerAdapter(this, null);
        mContactRecycler.setAdapter(mRecyclerAdapter);

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

    /**
     * Gets all contacts with person information using a
     * ContentResolver query.
     *
     * @return arraylist of constructed Contact objs.
     */
    private ArrayList<Contact> getAllContactsWithPersons() {
        final ContentResolver resolver = getContentResolver();
        Cursor cursor = resolver.query(
                ContactBookDatabaseContract.ContactTable.CONTACT_CONTENT_URI,
                null,null,null,null,null);

        ArrayList<Contact> contacts = buildContactsWithPerson(cursor);

        return contacts;
    }

    /**
     * Builds all the contacts with person information
     * using the cursor that is passed in.
     *
     * @param cursor the cursor with access to ContactTable join PersonTable.
     *
     * @return an arraylist of full build Contact objects.
     */
    private ArrayList<Contact> buildContactsWithPerson(Cursor cursor) {
        ArrayList<Contact> contacts = new ArrayList<>();

        if(cursor != null && cursor.moveToFirst()) {
            do {
                String email  = cursor.getString(EMAIL_COL);
                String home   = cursor.getString(HOME_COL);
                String mobile = cursor.getString(MOBILE_COL);
                String fName  = cursor.getString(F_NAME_COL);
                String mName  = cursor.getString(L_NAME_COL);
                String lName  = cursor.getString(M_NAME_COL);

                Person p  = Person.PersonBuilder.createPerson(fName, lName, mName);
                Contact c = Contact.ContactBuilder.createContact(null, p, email, home, mobile);
                contacts.add(c);
            } while (cursor.moveToNext());
        }

        if(!cursor.isClosed()) {
            cursor.close();
        }
        return contacts;
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
        mRecyclerAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mRecyclerAdapter.swapCursor(null);
    }
}
