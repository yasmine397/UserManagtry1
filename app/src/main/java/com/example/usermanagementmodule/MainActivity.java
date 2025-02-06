package com.example.usermanagementmodule;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {
private TextView etForgot,etSignUp,etLongin;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

    }
    protected void onStart(){
        super.onStart();
        gotoLoginFragment();

    }
    public void connect()
    {
        etForgot=findViewById(R.id.etForgot);
        etLongin=findViewById(R.id.etUsernameLogin);
        etLongin.setOnClickListener(view -> gotoLoginFragment());

    }

    private void gotoLoginFragment() {
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayout2,new LoginFragment());
        ft.commit();
    }

}

