package com.example.usermanagementmodule;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide; // Or use Picasso if you prefer
import com.example.usermanagementmodule.R;
import com.example.usermanagementmodule.book.Book;

import java.util.List;

public class LibraryBookAdapter extends RecyclerView.Adapter<LibraryBookAdapter.BookViewHolder> {
    private List<Book> bookList;

    public LibraryBookAdapter(List<Book> bookList) {
        this.bookList = bookList;
    }

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_library_book, parent, false);
        return new BookViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookName.setText(book.getName());
        holder.bookStatus.setText(book.getStatus()); // "want to read" or "read"
        // Load cover image (use Glide or Picasso)
        Glide.with(holder.itemView.getContext())
                .load(book.getPhoto())
                .placeholder(R.drawable.ic_book_placeholder)
                .into(holder.bookCover);
    }

    @Override
    public int getItemCount() {
        return bookList.size();
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        ImageView bookCover;
        TextView bookName, bookStatus;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCover = itemView.findViewById(R.id.book_cover);
            bookName = itemView.findViewById(R.id.book_name);
            bookStatus = itemView.findViewById(R.id.book_status);
        }
    }
}