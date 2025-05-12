package com.example.usermanagementmodule.book;

import static android.app.PendingIntent.getActivity;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usermanagementmodule.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.MyViewHolder> {
Context context;
ArrayList<Book> restList;
private OnBookClickListener BookClickListener;


    public BookAdapter(Context context, ArrayList<Book> restList) {
        this.context = context;
        this.restList = restList;
    }

    @NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(context).inflate(R.layout.item_book, parent, false);
    return new BookAdapter.MyViewHolder(v);
}


public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    Book book = restList.get(position);
    
    try {
        holder.tvbooknamebookItem.setText(book.getName());
        holder.tvlanguagebookItem.setText(book.getBooklan());
        holder.tvdatebookItem.setText(book.getRealestDate());
        holder.tvDeseridsionbookItem.setText(book.getDeseridsion());
        
        // Handle image loading with proper error handling
        String photoUrl = book.getPhoto();
        if (photoUrl != null && !photoUrl.isEmpty()) {
            try {
                if (photoUrl.startsWith("http") || photoUrl.startsWith("https")) {
                    // Load from network URL using Picasso
                    Picasso.get().load(photoUrl).into(holder.tvbookphoto);
                } else {
                    // Try to load from URI (local file)
                    Uri photoUri = Uri.parse(photoUrl);
                    holder.tvbookphoto.setImageURI(photoUri);
                }
            } catch (Exception e) {
                // Handle image loading error - set a default placeholder image
                holder.tvbookphoto.setImageResource(R.drawable.ic_book_placeholder);
                e.printStackTrace();
            }
        } else {
            // Set default image if no photo URL is provided
            holder.tvbookphoto.setImageResource(R.drawable.ic_book_placeholder);
        }
        
        // Set click listener
        holder.tvbooknamebookItem.setOnClickListener(v -> {
            if (BookClickListener != null) {
                BookClickListener.onItemClick(position);
            }
        });
    } catch (Exception e) {
        // Handle general binding errors
        e.printStackTrace();
    }
}

    public interface OnBookClickListener {
        void onItemClick(int position);
    }

    public void setOnBookClickListener(OnBookClickListener listener) {
        this.BookClickListener = listener;
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

