package ca.bc.northvan.armintoussi.contactbook.Activities;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import ca.bc.northvan.armintoussi.contactbook.Adapters.ContactRecyclerAdapter;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseContract;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactBookDatabaseHelper;
import ca.bc.northvan.armintoussi.contactbook.Database.ContactContentProvider;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.Models.Person;
import ca.bc.northvan.armintoussi.contactbook.R;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getName();

    private static final int EMAIL_COL  = 3;
    private static final int HOME_COL   = 4;
    private static final int MOBILE_COL = 4;
    private static final int F_NAME_COL = 7;
    private static final int L_NAME_COL = 8;
    private static final int M_NAME_COL = 9;

    private FloatingActionButton mFloatingAddContact;

    private Toolbar mToolbar;

    private RecyclerView mContactRecycler;
    private ContactRecyclerAdapter mRecyclerAdapter;
    private RecyclerView.LayoutManager mRecyclerLayoutManager;

    private ContactBookDatabaseHelper mHelper;
    private ContactContentProvider mProvider;

    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mHelper = ContactBookDatabaseHelper.getInstance(getApplicationContext());

        getViewReferences();
        setBtnListeners();
        setSupportActionBar(mToolbar);

        ArrayList<Contact> contacts = getAllContactsWithPersons();

        mContactRecycler.setHasFixedSize(true);

        mRecyclerLayoutManager = new LinearLayoutManager(this);
        mContactRecycler.setLayoutManager(mRecyclerLayoutManager);

        mRecyclerAdapter = new ContactRecyclerAdapter(contacts);
        mContactRecycler.setAdapter(mRecyclerAdapter);
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

}
