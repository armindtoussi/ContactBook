package ca.bc.northvan.armintoussi.contactbook.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;

/**
 * Created by armin2 on 4/25/2018.
 *
 * Recycler Adapter for a Cursor. This abstract class
 * provides most of the needs for a Recycler Adapter.
 * Needs to be subclassed and implement necessary methods.
 *
 */
public abstract class CursorRecyclerAdapter<VH extends RecyclerView.ViewHolder>
                                               extends RecyclerView.Adapter<VH>  {
    /** The context this can operate in. */
    private Context mContext;
    /** A cursor for data. */
    private Cursor mCursor;
    /** Bool for checking data validity. */
    private boolean mDataValid;
    /** Holds the _id for a row col. */
    private int mRowIdColumn;
    /** A dataset observer to observe changes to data. */
    private DataSetObserver mDataSetObserver;

    /**
     * ctor inits all instance variables and registers the observer.
     *
     * @param context calling context.
     * @param cursor  cursor to operate on.
     */
    public CursorRecyclerAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
        mDataValid = cursor != null;
        mRowIdColumn = mDataValid ? mCursor.getColumnIndex("_id") : -1;
        mDataSetObserver = new NotifyingDataSetObserver();
        if(mCursor != null) {
            mCursor.registerDataSetObserver(mDataSetObserver);
        }
    }

    /**
     * Gets the cursor that is active.
     *
     * @return the active Cursor.
     */
    public Cursor getCursor() {
        return mCursor;
    }

    /**
     * Gets the item count of the mContacts arraylist.
     *
     * @return the size of the arraylist as an int.
     */
    @Override
    public int getItemCount() {
        //todo maybe change this return.
        return (mDataValid && mCursor == null) ? 0 : mCursor.getCount();
    }

    /**
     * Gets the item id for specified position.
     *
     * @param position the position of the required id.
     *
     * @return the id of the required item.
     */
    @Override
    public long getItemId(int position) {
        if(mDataValid && mCursor != null && mCursor.moveToPosition(position)) {
            return mCursor.getLong((mRowIdColumn));
        }
        return 0;
    }

    /**
     * Sets if we have stable id's or not.
     *
     * @param hasStableIds boolean for if we have stable id's or not.
     */
    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(true);
    }

    /**
     * Binds the view holder to the parent view.
     * Overloaded onBindViewHolder method so we can use cursors instead in the
     * subclass that we provide.
     *
     * @param viewHolder the view holder.
     * @param cursor the cursor containing the query data.
     */
    public abstract void onBindViewHolder(VH viewHolder, Cursor cursor);

    /**
     * Binds the view holder to the parent view.
     *
     * @param holder the view holder.
     * @param position the position.
     */
    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (!mDataValid) {
            throw new IllegalStateException("This should only be called when the cursor is valid");
        }
        if (!mCursor.moveToPosition(position)) {
            throw new IllegalStateException("Couldn't move cursor to position " + position);
        }
        onBindViewHolder(holder, mCursor);
    }

    /**
     * Changes the cursor and closes the old one.
     *
     * @param cursor the cursor to change to.
     */
    public void changeCursor(Cursor cursor) {
        Cursor old = swapCursor(cursor);
        if (old != null) {
            old.close();
        }
    }

    /**
     * Swaps the cursor to a new one.
     * Registers the dataset observer.
     *
     * @param newCursor the cursor with new query data.
     *
     * @return the old cursor (*NOTE* old cursor is NOT closed).
     */
    public Cursor swapCursor(Cursor newCursor) {
        if (newCursor == mCursor) {
            return null;
        }

        final Cursor oldCursor = mCursor;
        if (oldCursor != null && mDataSetObserver != null) {
           oldCursor.unregisterDataSetObserver(mDataSetObserver);
        }

        mCursor = newCursor;

        if (mCursor != null) {
            if (mDataSetObserver != null) {
                mCursor.registerDataSetObserver(mDataSetObserver);
            }
            mRowIdColumn = newCursor.getColumnIndexOrThrow("_id");
            mDataValid = true;
            notifyDataSetChanged();
        } else {
            mRowIdColumn = -1;
            mDataValid = false;
            notifyDataSetChanged();
        }
        return oldCursor;
    }

    /**
     * A custom DataSetObserver for the cursor adapter.
     */
    private class NotifyingDataSetObserver  extends DataSetObserver {

        /**
         * Called when dataset is changed.
         */
        @Override
        public void onChanged() {
            super.onChanged();
            mDataValid = true;
            notifyDataSetChanged();
        }

        /**
         * Called when entire data becomes invalid.
         */
        @Override
        public void onInvalidated() {
            super.onInvalidated();
            mDataValid = false;
            notifyDataSetChanged();
        }
    }
}










