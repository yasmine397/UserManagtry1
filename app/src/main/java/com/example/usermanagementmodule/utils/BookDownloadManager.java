package com.example.usermanagementmodule.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import com.example.usermanagementmodule.book.Book;

/**
 * Utility class to handle book downloading functionality from external URLs
 */
public class BookDownloadManager {
    private static final String TAG = "BookDownloadManager";
    
    /**
     * Download a book cover from its URL
     * @param context Application context
     * @param book Book to download cover for
     * @return Download reference ID or -1 if failed
     */
    public static long downloadBook(Context context, Book book) {
        if (context == null || book == null) {
            Log.e(TAG, "Context or book is null");
            return -1;
        }
        
        String bookUrl = book.getPhoto();
        String bookName = book.getName();
        
        if (bookUrl == null || bookUrl.isEmpty()) {
            Log.e(TAG, "Book URL is empty or null");
            Toast.makeText(context, "No download URL available for this book", Toast.LENGTH_SHORT).show();
            return -1;
        }
        
        try {
            // Validate URL format
            Uri uri = Uri.parse(bookUrl);
            if (uri.getScheme() == null || !(uri.getScheme().equals("http") || uri.getScheme().equals("https"))) {
                Toast.makeText(context, "Invalid URL format: " + bookUrl, Toast.LENGTH_SHORT).show();
                return -1;
            }
            
            // Create download request
            DownloadManager.Request request = new DownloadManager.Request(uri)
                    .setTitle("Downloading " + bookName)
                    .setDescription("Downloading book cover")
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, 
                            "WatBook_" + bookName.replaceAll("[^a-zA-Z0-9]", "_") + ".jpg");
            
            // Get download service and enqueue the request
            DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            long downloadId = downloadManager.enqueue(request);
            
            Toast.makeText(context, "Downloading " + bookName, Toast.LENGTH_SHORT).show();
            return downloadId;
        } catch (Exception e) {
            Log.e(TAG, "Error downloading book: " + e.getMessage(), e);
            Toast.makeText(context, "Error starting download: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            return -1;
        }
    }
} 