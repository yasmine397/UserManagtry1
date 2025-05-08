package com.example.usermanagementmodule.book;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usermanagementmodule.Comment;
import com.example.usermanagementmodule.CommentAdapter;
import com.example.usermanagementmodule.R;
import com.example.usermanagementmodule.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_book_detail, container, false);
        ImageView BookCover=view.findViewById(R.id.bookCover);
        Bundle args = getArguments();
        if (args != null) {
            String title = args.getString("title");
            String coverUrl = args.getString("coverUrl");
            String description = args.getString("description");

            // لتحميل صورة الغلاف من رابط coverUrl
            Picasso.get().load(coverUrl).into(BookCover); // أو أي مكتبة تحميل صور
        }
        EditText commentInput = view.findViewById(R.id.commentInput);
        Button sendButton = view.findViewById(R.id.buttonSend);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerViewComments);

        List<Comment> commentList = new ArrayList<>();
        CommentAdapter adapter = new CommentAdapter(commentList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                if (firebaseUser != null) {
                    String username = firebaseUser.getDisplayName();
                    String imageUrl = firebaseUser.getPhotoUrl() != null ? firebaseUser.getPhotoUrl().toString() : "";
                    String commentText = commentInput.getText().toString().trim();

                    if (!commentText.isEmpty()) {
                        User currentUser = new User(username, imageUrl);
                        Comment newComment = new Comment(currentUser, commentText);
                        commentList.add(newComment);

                        adapter.notifyItemInserted(commentList.size() - 1);
                        recyclerView.scrollToPosition(commentList.size() - 1);
                        commentInput.setText("");
                    }
                } else {
                    Toast.makeText(getContext(), "please log in frist", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;

    }

}
