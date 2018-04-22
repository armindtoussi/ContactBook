package ca.bc.northvan.armintoussi.contactbook.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ca.bc.northvan.armintoussi.contactbook.Models.Contact;
import ca.bc.northvan.armintoussi.contactbook.R;

/**
 * Created by armin2 on 4/22/2018.
 */

public class ContactRecyclerAdapter extends RecyclerView.Adapter<ContactRecyclerAdapter.ContactHolder> {

    private String[] mDatasetName;
    private String[] mDataNum;

    public ContactRecyclerAdapter(String[] dataset, String[] datanum) {
        mDatasetName = dataset;
        mDataNum = datanum;
    }


    @Override
    public ContactRecyclerAdapter.ContactHolder onCreateViewHolder(ViewGroup parent,int viewType) {
        //Create a new view
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.contact_holder, parent, false);

        ContactHolder vh = new ContactHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ContactHolder holder, int position) {
        //get element from your dataset at this position
        //replace the contents of the view with that element\
        final String name = mDatasetName[position];
        final String num  = mDataNum[position];

        holder.mNameText.setText(name);
        holder.mNumText.setText(num);
    }

    @Override
    public int getItemCount() {
        return mDatasetName.length;
    }


    public static class ContactHolder extends RecyclerView.ViewHolder {
        public TextView mNameText;
        public TextView mNumText;
        public View mLayout;

        public ContactHolder(View v) {
            super(v);
            mLayout = v;
            mNameText = v.findViewById(R.id.contact_item_text);
            mNumText = v.findViewById(R.id.contact_number);
        }
    }
}
