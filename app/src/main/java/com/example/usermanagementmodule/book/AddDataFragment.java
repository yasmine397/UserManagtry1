package com.example.usermanagementmodule.book;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usermanagementmodule.FirebaseServices;
import com.example.usermanagementmodule.R;
import com.example.usermanagementmodule.Utils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddDataFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDataFragment extends Fragment {
    private static final int GALLARY_REQUEST_CODE = 123;
    private EditText etBName,etBDate,etBDeserid,etBLan;
    private Button btnAdd;
    private FirebaseServices fbs;
    private TextView Start;
    private ImageView img;
    private Utils msg ;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddDataFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static AddDataFragment newInstance(String param1, String param2) {
        AddDataFragment fragment = new AddDataFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_book, container, false);
    }
    @Override
    public  void  onStart(){
        super.onStart();
        connectComponents();
    }
    private void connectComponents(){
        etBName=getActivity().findViewById(R.id.etMainAddBookN);
        etBDate=getActivity().findViewById(R.id.etrealestDate);
        etBDeserid=getActivity().findViewById(R.id.etMainDeseridsion);
        etBLan=getActivity().findViewById(R.id.etMainBookLan);
        btnAdd=getActivity().findViewById((R.id.btnAdd));
        fbs=FirebaseServices.getInstance();
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallerIntent =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallerIntent,GALLARY_REQUEST_CODE);
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name,date,deserid,blan;
                name=etBName.getText().toString();
                date=etBDate.getText().toString();
                deserid=etBDeserid.getText().toString();
                blan=etBLan.getText().toString();


                if(name.trim().isEmpty()||date.trim().isEmpty()||deserid.trim().isEmpty()||blan.trim().isEmpty())
                {
                    Toast.makeText(getActivity(), "some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Book book=new Book(name,date,deserid,blan,"");
                fbs.getFire().collection("books").add(book).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                   public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                     //what to do in  success
                    }
                 }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //what to do in failure
                        Toast.makeText(getActivity(), "Fff", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name1 = etBName.getText().toString();
                String date1 =etBDate.getText().toString();
                String deserid1 = etBDeserid.getText().toString();
                String lan1 = etBLan.getText().toString();
                if (name1.isEmpty() || date1.isEmpty() || deserid1.isEmpty() || lan1.isEmpty())
                {
                    Toast.makeText(getActivity(), "Some fields are empty!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Uri selectedImageUri=fbs.getSelectedImageURL();
                String imageUri = "";
                if(selectedImageUri!=null)
                    imageUri=selectedImageUri.toString();
                Book user = new Book(imageUri.toString(),name1, date1, deserid1, lan1);
                fbs.getFire().collection("Users").add(user).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getActivity(), "welcome", Toast.LENGTH_SHORT).show();
                        FragmentTransaction transaction= getParentFragmentManager().beginTransaction();
                        transaction.replace(R.id.etMainAddBookN,new HomeFragment());
                        transaction.commit();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(), "faild ", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode ==  GALLARY_REQUEST_CODE && resultCode == getActivity().RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            img.setImageURI(selectedImageUri);
            Utils.getInstance().uploadImage(getActivity(), selectedImageUri);
        }
    }
}



