package com.example.rooms_avaliability_module;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.rooms_avaliability_module.ui.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends Fragment {

    private ProfileViewModel mViewModel;

    public static Profile newInstance() {
        return new Profile();
    }
View root;
    EditText edt,edt1,nam;
    Button name,prn,Email,pass,logout;
   public String fin;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference,base;
    FirebaseDatabase firebaseDatabase;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root=inflater.inflate(R.layout.profile_fragment, container, false);
        edt = root.findViewById(R.id.editText3);
        edt1 = root.findViewById(R.id.editText4);
        nam = root.findViewById(R.id.editText2);
        name = root.findViewById(R.id.btnName);
        prn = root.findViewById(R.id.btnPrn);
        Email = root.findViewById(R.id.btnMail);
        pass = root.findViewById(R.id.btnPass);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = firebaseDatabase.getReference().child("RoomsAvalibility").child("User");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.getValue().toString().compareTo(firebaseUser.getUid())==0)
                    {
                       // edt.setText(snapshot.getKey());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    //    edt1.setText(firebaseUser.getEmail());
        base = firebaseDatabase.getReference();
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                base.child("UserNames").child(edt.getText().toString()).setValue(nam.getText().toString());
            }
        });


        prn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        Email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        logout = root.findViewById(R.id.button2);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             firebaseAuth=   FirebaseAuth.getInstance();
             FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);

            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}
