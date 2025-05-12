package com.example.usermanagementmodule;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagementmodule.Main.sampledata.FirebaseServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.firestore.DocumentSnapshot;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    private static final String TAG = "LoginFragment"; // Tag for logging
    
    private EditText etUsername, etPassword, etForgot;
    private FirebaseServices fbs;
    private TextView tvSignupLink;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        //connecting components
        fbs = FirebaseServices.getInstance();
        etUsername = getView().findViewById(R.id.etUsernameLogin);
        etPassword = getView().findViewById(R.id.etPasswordLogin);
        Button btnLogin = getView().findViewById(R.id.btnLoginLogin);
        tvSignupLink = getView().findViewById(R.id.tvSignupLogin);
        TextView tvForgotLink = getView().findViewById(R.id.etForgot);
        
        // Check if user is already logged in
        if (fbs.getAuth().getCurrentUser() != null) {
            // User is already signed in, fetch their data
            loadUserData(fbs.getAuth().getCurrentUser().getUid());
        }
        
        tvForgotLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoForgotpassFragment();
            }
        });
        
        tvSignupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoSignupFragment();
            }
        });
        
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Data validation
                String email = etUsername.getText().toString();
                String pass = etPassword.getText().toString();
                
                if (email.trim().isEmpty() || pass.trim().isEmpty()) {
                    Toast.makeText(getActivity(), "Email and password cannot be empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                
                // Login procedure
                fbs.getAuth().signInWithEmailAndPassword(email, pass).addOnSuccessListener(
                        new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {
                                Toast.makeText(getActivity(), "Login successful", Toast.LENGTH_SHORT).show();
                                // Get the user data from Firestore
                                String userId = fbs.getAuth().getCurrentUser().getUid();
                                loadUserData(userId);
                            }
                        }
                ).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Login failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    
    private void loadUserData(String userId) {
        fbs.getFire().collection("users").document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()) {
                            // Convert to DataUser object
                            User user = documentSnapshot.toObject(User.class);
                            
                            // Set the current user in FirebaseServices
                            FirebaseServices.getInstance().setCurrentUser(user);
                            
                            // Navigate to home screen
                            gotoHomeFragment();
                        } else {
                            Toast.makeText(getActivity(), "User data not found", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "Error loading user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void gotoHomeFragment() {
        try {
            if (getActivity() == null) {
                Log.e(TAG, "getActivity() returned null. Fragment may be detached.");
                return;
            }
            
            FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout2, new HomeFragment());
            ft.commit();
            Log.d(TAG, "Successfully navigated to HomeFragment");
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to HomeFragment: " + e.getMessage(), e);
            if (getContext() != null) {
                Toast.makeText(getContext(), "Error navigating to home screen", Toast.LENGTH_LONG).show();
            }
        }
    }
    
    private void gotoAddBookFragment() {
        gotoHomeFragment();
    }

    private void gotoSignupFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout2, new SignupFragment());
        ft.commit();
    }

    private void gotoForgotpassFragment() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout2, new ForgotFragment());
        ft.commit();
    }
}