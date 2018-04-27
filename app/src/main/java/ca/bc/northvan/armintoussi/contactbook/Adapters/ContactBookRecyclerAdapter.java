package ca.bc.northvan.armintoussi.contactbook.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.bc.northvan.armintoussi.contactbook.Activities.EditContactActivity;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.R;

/**
 * Created by armin2 on 4/25/2018.
 *
 * ContactBookRecycler adapter is subclassed from CursorRecyclerAdapter.
 * Overrides the necessary methods for the needs of the adapter for
 * the RecyclerView.
 *
 */
public class ContactBookRecyclerAdapter extends CursorRecyclerAdapter<ContactBookRecyclerAdapter.ContactHolder> {
    /** Click Listener for each Contact Holder. */
    private final View.OnClickListener mOnClickListener = new ContactHolderOnClickListener();
    /** A reference to the whole Recycler View. */
    private RecyclerView mRecyclerView;
    /**  An array list of the Contact objs added to the view. */
    private ArrayList<Contact> mContacts;

    /**
     * Creates a Recycler Adapter.
     *
     * @param context the calling context.
     * @param cursor  the cursor to operate one.
     */
    public ContactBookRecyclerAdapter(Context context, Cursor cursor, RecyclerView view) {
        super(context, cursor);
        mRecyclerView = view;
        mContacts = new ArrayList<>();
    }

    /**
     * Creates the view holder for binding to the parent.
     *
     * @param parent the parent view group.
     * @param viewType the position.
     *
     * @return a constructed view holder.
     */
    @Override
    public ContactHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.contact_holder, parent, false);
        v.setOnClickListener(mOnClickListener);
        ContactHolder vh = new ContactHolder(v);
        return vh;
    }

    /**
     * When view holder is bound, this method binds the data
     * to the view holder. Also adds the contact to an
     * array list.
     *
     * @param vh the view holder.
     * @param cursor the cursor containing the query data.
     */
    @Override
    public void onBindViewHolder(ContactHolder vh, Cursor cursor) {
        Contact contact = Contact.fromCursor(cursor);
        mContacts.add(contact);

        final String name = contact.getPerson().getFirstName() + " "
                + contact.getPerson().getLastName();

        vh.mNameText.setText(name);
        vh.mNumText.setText(contact.getMobilePhoneNumber());
    }

    /**
     * Contact holder class represents a single view
     * for the recycler.
     */
    public static class ContactHolder extends RecyclerView.ViewHolder {
        /** The text view for the person's name.  */
        public TextView mNameText;
        /** The text view for the person's mobile number. */
        public TextView mNumText;
        /** The container for the text views. */
        public View mLayout;

        /**
         * Basic ctor for the holder.
         *
         * @param v the view.
         */
        public ContactHolder(View v) {
            super(v);
            mLayout = v;
            mNameText = v.findViewById(R.id.contact_item_text);
            mNumText = v.findViewById(R.id.contact_number);
        }
    }

    /**
     * A Listener for the Contact Holders in our Recycler view.
     *
     */
    private class ContactHolderOnClickListener implements View.OnClickListener {
        /**
         * On Click method of the listener grabs the Contact _id of the
         * clicked element and pass it to the edit contact activity.
         *
         * @param view the view that was clicked.
         */
        @Override
        public void onClick(final View view) {
            int itemPosition = mRecyclerView.getChildLayoutPosition(view);
            Toast.makeText(mContext, "Item was clicked " + itemPosition, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(mContext, EditContactActivity.class);
            intent.putExtra("contact", mContacts.get(itemPosition).get_id());
            mContext.startActivity(intent);
        }
    }
}
