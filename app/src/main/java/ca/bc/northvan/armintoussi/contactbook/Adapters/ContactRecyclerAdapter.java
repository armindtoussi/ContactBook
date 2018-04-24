package ca.bc.northvan.armintoussi.contactbook.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.R;

/**
 * Created by armin2 on 4/22/2018.
 *
 * Adapter class for the recycler view.
 * Populates the view holders and adds them to the recycler view.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactHolder> {
    /** Arraylist of contacts. */
    private ArrayList<Contact> mContacts;

    /**
     * Constructs an instance of our adapter.
     *
     * @param contacts arraylist of contact objs.
     */
    public ContactRecyclerAdapter(ArrayList<Contact> contacts) {
        mContacts = contacts;
    }

    /**
     * Creates the view holder for each item.
     *
     * @param parent   the parent view group.
     * @param viewType the type of view.
     *
     * @return a constructed ContactHolder;
     */
    @Override
    public ContactRecyclerAdapter.ContactHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.contact_holder, parent, false);

        ContactHolder vh = new ContactHolder(v);
        return vh;
    }

    /**
     * Binds the ContactHolder to the view.
     *
     * @param holder the holder.
     * @param position the position of this holder;
     */
    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        final String name = mContacts.get(position).getPerson().getFirstName() + " "
                          + mContacts.get(position).getPerson().getLastName();
        final String num  = mContacts.get(position).getMobilePhoneNumber();

        holder.mNameText.setText(name);
        holder.mNumText.setText(num);
    }

    /**
     * Gets the item count of the mContacts arraylist.
     *
     * @return the size of the arraylist as an int.
     */
    @Override
    public int getItemCount() {
        return mContacts.size();
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
}
