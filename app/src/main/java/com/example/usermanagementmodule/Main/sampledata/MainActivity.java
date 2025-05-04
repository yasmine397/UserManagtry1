package com.example.usermanagementmodule.Main.sampledata;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.usermanagementmodule.HomeFragment;
import com.example.usermanagementmodule.LoginFragment;
import com.example.usermanagementmodule.R;
import com.example.usermanagementmodule.SignupFragment;
import com.example.usermanagementmodule.book.AddDataFragment;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private TextView watst, bookst;
    private Button btnSignup, btnLogin;
    private FirebaseServices fbs;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        
        // Initialize Firebase Services
        fbs = FirebaseServices.getInstance();
        
        // Connect views
        connect();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
        // Check if user is already logged in
        if (fbs.isLoggedIn()) {
            // User is already signed in, go to HomeFragment
            Log.d(TAG, "onStart: User already logged in, navigating to HomeFragment");
            gotoHomeFragment();
        } else {
            // Not logged in, show welcome screen with login/signup options
            Log.d(TAG, "onStart: User not logged in, showing welcome screen");
            setupWelcomeScreen();
        }
    }
    
    private void setupWelcomeScreen() {
        // Setup button click listeners
        btnSignup.setOnClickListener(view -> gotoSignupFragment());
        btnLogin.setOnClickListener(view -> gotoLoginFragment());
    }
    
    public void connect() {
        watst = findViewById(R.id.watst);
        bookst = findViewById(R.id.bookst);
        btnSignup = findViewById(R.id.bnsignupst);
        btnLogin = findViewById(R.id.bnloginst);
    }

    private void gotoLoginFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout2, new LoginFragment());
        ft.commit();
    }
    
    private void gotoSignupFragment() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout2, new SignupFragment());
        ft.commit();
    }
    
    // Keep this method for backward compatibility, but redirect to HomeFragment
    private void gotoAddBookFragment() {
        gotoHomeFragment();
    }
    
    private void gotoHomeFragment() {
        try {
            Log.d(TAG, "gotoHomeFragment: Navigating to HomeFragment");
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.frameLayout2, new HomeFragment());
            ft.commit();
        } catch (Exception e) {
            Log.e(TAG, "gotoHomeFragment: Error navigating to HomeFragment", e);
        }
    }
}

