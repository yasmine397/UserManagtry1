package com.example.usermanagementmodule;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
Context context;
ArrayList<Book> restList;
private FirebaseServices fbs;


public void DataUserAdapter(Context context, ArrayList<Book> restList) {
    this.context = context;
    this.restList = restList;
    this.fbs = FirebaseServices.getInstance();
}


@NonNull
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
    return new BookAdapter.MyViewHolder(v);
}


public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Book rest = restList.get(position);
    holder.tvbooknamebookItem.setText(rest.getName());
    holder.tvlanguagebookItem.setText(rest.getBooklan());
    holder.tvdatebookItem.setText(rest.getRealestDate());
    holder.tvDeseridsionbookItem.setText(rest.getDeseridsion());
    holder.tvbookphoto.setImageURI(rest.getPhoto());

}


private void startActivityForResult(Intent galleryIntent, int galleryRequestCode) {
}


public int getItemCount() {
    return restList.size();
}

public static class MyViewHolder extends RecyclerView.ViewHolder {
    public Object tvbookphone;
    TextView tvbooknamebookItem, tvDeseridsionbookItem, tvdatebookItem, tvlanguagebookItem;
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

}

