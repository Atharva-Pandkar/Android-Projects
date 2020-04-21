package com.example.comaq.geo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Sign_Up extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String name, email, password, confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    public void SubmitClicked(View view) {
        EditText name = findViewById(R.id.editTextName);
        EditText email = findViewById(R.id.editTextEmail);
        EditText password = findViewById(R.id.editTextPassword);
        EditText confirm = findViewById(R.id.editTextConfirm);
        this.name = name.getText().toString();
        this.email = email.getText().toString();
        this.password = password.getText().toString();
        this.confirm = confirm.getText().toString();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Account Details");
        databaseReference.child(this.name).child("Email").setValue(this.email);
        databaseReference.child(this.name).child("Password").setValue(this.password);
        if (this.password.compareTo(this.confirm) == 0) {
            Toast.makeText(this, "Registered", Toast.LENGTH_LONG).show();
            firebaseAuth.createUserWithEmailAndPassword(this.email, this.password);

            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
            intent.putExtra("Name", this.name);

        }
    }
}
