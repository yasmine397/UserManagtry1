package com.example.usermanagementmodule.book;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
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

public class BookGridAdapter extends RecyclerView.Adapter<BookGridAdapter.BookViewHolder> {
    private static final String TAG = "BookGridAdapter";
    private final Context context;
    private final ArrayList<Book> books;
    private OnBookClickListener onBookClickListener;

    public BookGridAdapter(Context context, ArrayList<Book> books) {
        this.context = context;
        this.books = books;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_grid_book, parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = books.get(position);
        
        try {
            // Set book title
            holder.tvBookTitle.setText(book.getName());
            
            // Load book cover image
            String photoUrl = book.getPhoto();
            if (photoUrl != null && !photoUrl.isEmpty()) {
                try {
                    Picasso.get()
                            .load(photoUrl)
                            .placeholder(R.drawable.ic_book_placeholder)
                            .error(R.drawable.ic_book_placeholder)
                            .into(holder.ivBookCover);
                } catch (Exception e) {
                    Log.e(TAG, "Error loading book cover: " + e.getMessage());
                    holder.ivBookCover.setImageResource(R.drawable.ic_book_placeholder);
                }
            } else {
                holder.ivBookCover.setImageResource(R.drawable.ic_book_placeholder);
            }
            
            // Set click listener
            holder.itemView.setOnClickListener(v -> {
                if (onBookClickListener != null) {
                    onBookClickListener.onBookClick(position, book);
                }
            });
        } catch (Exception e) {
            Log.e(TAG, "Error binding book view: " + e.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setOnBookClickListener(OnBookClickListener listener) {
        this.onBookClickListener = listener;
    }

    public interface OnBookClickListener {
        void onBookClick(int position, Book book);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBookCover;
        TextView tvBookTitle;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            ivBookCover = itemView.findViewById(R.id.ivBookCover);
            tvBookTitle = itemView.findViewById(R.id.tvBookTitle);
        }
    }
} 