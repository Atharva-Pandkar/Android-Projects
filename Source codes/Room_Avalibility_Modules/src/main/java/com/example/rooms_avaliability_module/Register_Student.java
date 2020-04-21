package com.example.rooms_avaliability_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register_Student extends AppCompatActivity {
FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser!=null)
        {
            Intent intent = new Intent(getApplicationContext(),Drawer.class);
            startActivity(intent);
        }
        setContentView(R.layout.activity_register__student);

    }
    String uid,str;
    int t;
    EditText prn,mail,pass,repass,name;
    FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
   public  void  btnClicked (View view)
    {
        prn = findViewById(R.id.prn);
        mail  = findViewById(R.id.email);
        name = findViewById(R.id.name);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        pass = findViewById(R.id.pass);
        repass = findViewById(R.id.rePass);

            if (pass.getText().toString().compareTo(repass.getText().toString()) == 0) {
                firebaseAuth.createUserWithEmailAndPassword(mail.getText().toString(), pass.getText().toString()).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                   t=5;
                    }
                }).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       t=4;
                    }
                });
                firebaseAuth.signInWithEmailAndPassword(mail.getText().toString(),pass.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    t=6;
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        str=e.getMessage();
                    }
                });
                uid=FirebaseAuth.getInstance().getUid();
                databaseReference.child(uid).child(prn.getText().toString()).setValue(pass.getText().toString());
                databaseReference.child(uid).child("Mail").setValue(mail.getText().toString());
                databaseReference.child(uid).child("Name").setValue(name.getText().toString());

            }

        Intent intent = new Intent(this,Drawer.class);
        startActivity(intent);
    }
}
