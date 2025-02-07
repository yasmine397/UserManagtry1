package com.example.usermanagementmodule;

import static android.app.PendingIntent.getActivity;
import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class BookAdapter {
    private static final int GALLERY_REQUEST_CODE = 123;
    Context context;
    ArrayList<Book> restList;
    private FirebaseServices fbs;


    public void DataUserAdapter(Context context, ArrayList<Book> restList) {
        this.context = context;
        this.restList = restList;
        this.fbs = FirebaseServices.getInstance();
    }


    @NonNull
    public BookAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
        return new BookAdapter.MyViewHolder(v);
    }

//
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Book rest = restList.get(position);
        holder.tvbooknamebookItem.setText(rest.getName());
        holder.tvlanguagebookItem.setText(rest.getBooklan());
        holder.tvdatebookItem.setText(rest.getRealestDate());
        holder.tvDeseridsionbookItem.setText(rest.getDeseridsion());
        holder.tvbookphoto.setImageURI(rest.getPhoto());

    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE);
    }

    private void startActivityForResult(Intent galleryIntent, int galleryRequestCode) {
    }


    public int getItemCount() {
        return restList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public Object tvbookphone;
        TextView tvbooknamebookItem, tvDeseridsionbookItem,tvdatebookItem,tvlanguagebookItem;
        ImageView tvbookphoto;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvbookphoto = itemView.findViewById(R.id.tvbookphoto);
            tvbooknamebookItem = itemView.findViewById(R.id.tvbooknamebookItem);
            tvdatebookItem = itemView.findViewById(R.id.tvdatebookItem);
            tvDeseridsionbookItem = itemView.findViewById(R.id.tvDeseridsionbookItem);
            tvlanguagebookItem = itemView.findViewById(R.id.tvlanguagebookItem);

        }
    }

    //addUserToCompany(companies, user);
    public void uploadImage(Uri selectedImageUri) {

    }

    private Context getActivity() {
    }
}

