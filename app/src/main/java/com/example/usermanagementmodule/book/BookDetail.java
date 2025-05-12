package com.example.usermanagementmodule.book;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagementmodule.Comment;
import com.example.usermanagementmodule.CommentAdapter;
import com.example.usermanagementmodule.R;
import com.example.usermanagementmodule.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.SetOptions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BookDetail#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookDetail extends Fragment {
    private EditText commentEditText;
    private Button sendButton;
    private RecyclerView commentsRecyclerView;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> commentList;
    private RatingBar ratingBarAverage, ratingBarUser;
    private TextView ratingValueText;
    private FirebaseFirestore db;
    private FirebaseUser currentUser;
    private String bookId;
    private Spinner bookStatusSpinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BookDetail() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BookDetail.
     */
    // TODO: Rename and change types and number of parameters
    public static BookDetail newInstance(String param1, String param2) {
        BookDetail fragment = new BookDetail();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);

        // Book cover
        ImageView bookCover = view.findViewById(R.id.bookCover);

        // Get arguments
        Bundle args = getArguments();
        if (args != null) {
            bookId = args.getString("bookId");
            String title = args.getString("title");
            String coverUrl = args.getString("coverUrl");
            String description = args.getString("description");
            Picasso.get().load(coverUrl).into(bookCover);
        }

        // Comments
        commentEditText = view.findViewById(R.id.commentInput);
        sendButton = view.findViewById(R.id.buttonSend);
        commentsRecyclerView = view.findViewById(R.id.recyclerViewComments);

        commentList = new ArrayList<>();
        commentAdapter = new CommentAdapter(commentList);
        commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        commentsRecyclerView.setAdapter(commentAdapter);

        bookStatusSpinner = view.findViewById(R.id.book_status_spinner);
        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.book_status_options,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bookStatusSpinner.setAdapter(spinnerAdapter);

        bookStatusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedStatus = parent.getItemAtPosition(position).toString();
                saveBookStatus(selectedStatus);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        // Ratings
        ratingBarUser = view.findViewById(R.id.ratingBarUser);
        ratingBarAverage = view.findViewById(R.id.ratingBarAverage);
        ratingValueText = view.findViewById(R.id.ratingValueText);

        db = FirebaseFirestore.getInstance();
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        // Load average rating
        loadAverageRating();

        // Send comment
        sendButton.setOnClickListener(v -> {
            if (currentUser != null) {
                String username = currentUser.getDisplayName();
                String imageUrl = currentUser.getPhotoUrl() != null ? currentUser.getPhotoUrl().toString() : "";
                String commentText = commentEditText.getText().toString().trim();

                if (!commentText.isEmpty()) {
                    User user = new User(username, imageUrl);
                    Comment newComment = new Comment(user, commentText);

                    // Save to Firestore
                    Map<String, Object> commentData = new HashMap<>();
                    commentData.put("userId", currentUser.getUid());
                    commentData.put("userName", username);
                    commentData.put("userPhotoUrl", imageUrl);
                    commentData.put("bookId", bookId);
                    commentData.put("commentText", commentText);
                    commentData.put("timestamp", com.google.firebase.firestore.FieldValue.serverTimestamp());

                    db.collection("comments")
                            .add(commentData)
                            .addOnSuccessListener(documentReference -> {
                                Toast.makeText(getContext(), "Comment added!", Toast.LENGTH_SHORT).show();
                                commentEditText.setText("");
                                loadComments();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Failed to add comment.", Toast.LENGTH_SHORT).show();
                            });
                }
            } else {
                Toast.makeText(getContext(), "Please log in first", Toast.LENGTH_SHORT).show();
            }
        });

        // User rating
        ratingBarUser.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
            if (fromUser && currentUser != null) {
                Map<String, Object> ratingData = new HashMap<>();
                ratingData.put("bookId", bookId);
                ratingData.put("userId", currentUser.getUid());
                ratingData.put("rating", (int) rating);

                db.collection("ratings")
                        .document(bookId + "_" + currentUser.getUid())
                        .set(ratingData, SetOptions.merge())
                        .addOnSuccessListener(unused -> {
                            Toast.makeText(getContext(), "Rating submitted!", Toast.LENGTH_SHORT).show();
                            loadAverageRating();
                        });
            }
        });
        loadBookStatus();

        return view;
    }

    private void saveBookStatus(String status) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && bookId != null) {
            Map<String, Object> statusData = new HashMap<>();
            statusData.put("userId", currentUser.getUid());
            statusData.put("bookId", bookId);
            statusData.put("status", status);

            db.collection("user_book_status")
                    .document(bookId + "_" + currentUser.getUid())
                    .set(statusData, SetOptions.merge());
        }
    }

    private void loadBookStatus() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null && bookId != null) {
            db.collection("user_book_status")
                    .document(bookId + "_" + currentUser.getUid())
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            String status = documentSnapshot.getString("status");
                            if (status != null) {
                                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                                        getContext(),
                                        R.array.book_status_options,
                                        android.R.layout.simple_spinner_item
                                );
                                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                bookStatusSpinner.setAdapter(adapter);

                                int position = adapter.getPosition(status);
                                if (position >= 0) {
                                    bookStatusSpinner.setSelection(position);
                                }
                            }
                        }
                    });
        }
    }


    private void loadComments() {
        db.collection("comments")
                .whereEqualTo("bookId", bookId)
                .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.ASCENDING)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    commentList.clear();
                    for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                        String userName = doc.getString("userName");
                        String userPhotoUrl = doc.getString("userPhotoUrl");
                        String commentText = doc.getString("commentText");
                        User user = new User(userName, userPhotoUrl);
                        Comment comment = new Comment(user, commentText);
                        commentList.add(comment);
                    }
                    commentAdapter.notifyDataSetChanged();
                });
    }

    private void loadAverageRating() {
        db.collection("ratings")
                .whereEqualTo("bookId", bookId)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    int totalRating = 0;
                    int count = 0;

                    for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                        Long rate = document.getLong("rating");
                        if (rate != null) {
                            totalRating += rate;
                            count++;
                        }
                    }

                    if (count > 0) {
                        float average = (float) totalRating / count;
                        ratingBarAverage.setRating(average);
                        ratingValueText.setText(String.format("%.1f", average));
                    } else {
                        ratingBarAverage.setRating(0);
                        ratingValueText.setText("0.0");
                    }
                });

    }
}


