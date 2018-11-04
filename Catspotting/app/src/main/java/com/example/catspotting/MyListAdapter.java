package com.example.catspotting;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MyListAdapter extends RecyclerView.Adapter<MyListAdapter.MyViewHolder> {
    private String[] mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView username;
        public TextView description;
        public ImageView imageView;
        public TextView tags;
        public Button loveButton;
        public Button nopeButton;
        public MyViewHolder(View postView) {
            super(postView);

            username = (TextView)postView.findViewById(R.id.username_text_view);
            description = (TextView)postView.findViewById(R.id.description_text_view);
            imageView = (ImageView)postView.findViewById(R.id.cat_picture);
            tags = (TextView)postView.findViewById(R.id.tags_text_view);
            loveButton = (Button)postView.findViewById(R.id.love_button);
            nopeButton = (Button)postView.findViewById(R.id.nope_button);

        }

        public void loadWithGlide(String image) {
            // [START storage_load_with_glide]
            FirebaseStorage storage = FirebaseStorage.getInstance();
            System.out.println("Storage: " + storage);

            // Create a storage reference from our app
            StorageReference storageRef = storage.getReference();

            // Create a reference with an initial file path and name
            StorageReference gsReference = storage.getReferenceFromUrl("gs://catspotting-5ae47.appspot.com/" + image);

            System.out.println("Starting to try.");

            // Download directly from StorageReference using Glide
            // (See MyAppGlideModule for Loader registration)
            GlideApp.with(imageView.getContext() /* context */)
                    .load(gsReference)
                    .centerCrop()
                    .placeholder(R.drawable.computer_loading_symbol)
                    .into(imageView);
            // [END storage_load_with_glide]
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MyListAdapter(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.post, parent, false);
        return new MyViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.description.setText(mDataset[position]);
        holder.loadWithGlide(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}