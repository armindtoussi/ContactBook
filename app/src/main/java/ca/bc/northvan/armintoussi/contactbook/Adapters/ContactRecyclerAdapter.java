package ca.bc.northvan.armintoussi.contactbook.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import ca.bc.northvan.armintoussi.contactbook.Activities.HomeActivity;
import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.R;

/**
 * Created by armin2 on 4/22/2018.
 *
 * Adapter class for the recycler view.
 * Populates the view holders and adds them to the recycler view.
 */
public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactHolder> {
    /** Debug class tag. */
    private static final String TAG = ContactRecyclerAdapter.class.getName();

    private Cursor mCursor;
    private Context mContext;
    /**
     * Constructs an instance of our adapter.
     *
     * @param context the context
     * @param cursor the current cursor.
     */
    public ContactRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor   = cursor;
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
        mCursor.moveToPosition(position);
        final String name = mCursor.getString(HomeActivity.F_NAME_COL) + " "
                          + mCursor.getString(HomeActivity.L_NAME_COL);
        final String num  = mCursor.getString(HomeActivity.MOBILE_COL);

        holder.mNameText.setText(name);
        holder.mNumText.setText(num);
    }

    public Cursor swapCursor(Cursor cursor) {
        if(mCursor == cursor) {
            Log.i(TAG, "this is not a new cursor");
            return null;
        }

        Cursor oldCursor = mCursor;
        this.mCursor = cursor;
        if(cursor != null) {
            Log.i(TAG, "Trying to notify the dataset changed");
            this.notifyDataSetChanged();
        }
        return oldCursor;
    }

    /**
     * Gets the item count of the mContacts arraylist.
     *
     * @return the size of the arraylist as an int.
     */
    @Override
    public int getItemCount() {
        //todo maybe change this return.
        return (mCursor == null) ? 0 : mCursor.getCount();
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
