package com.example.comaq.geo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String passw;
    int ch;
    String userName, password;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION , 1, 1) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.INTERNET,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
            }, 1);
        }
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
    }

    public void LoginClicked(View view)
    {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Account Details");
        EditText usr = findViewById(R.id.txtEmail);
        EditText pass = findViewById(R.id.txtPassword);
        userName = usr.getText().toString();
        password = pass.getText().toString();
        firebaseUser = firebaseAuth.getCurrentUser();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    passw = snapshot.child("Password").getValue().toString();
                    if (password.compareTo(passw) == 0) {
                        firebaseAuth.signInWithEmailAndPassword(userName, password);
                       // firebaseAuth.updateCurrentUser(firebaseUser);
                        ch=1;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        Intent intent = new Intent(this,Home.class);
        if(ch==1)
        startActivity(intent);
    }
    public void SignUpClicked(View view)
    { EditText usr = findViewById(R.id.txtEmail);
        EditText pass = findViewById(R.id.txtPassword);
        userName = usr.getText().toString();
        password = pass.getText().toString();

        if (userName.compareTo("admin") == 0 && password.compareTo("123") ==0)
        {
            Intent intent = new Intent (this,AdminPrivilages.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, Sign_Up.class);
            startActivity(intent);

        }}
}
