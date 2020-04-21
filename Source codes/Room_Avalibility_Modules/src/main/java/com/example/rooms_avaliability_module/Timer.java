package com.example.rooms_avaliability_module;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.sql.Time;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class Timer extends AppCompatActivity {
    TextView name;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference,userss,databaseReference1,databaseReferenceadmin,databaseReferencehistory;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
       name=findViewById(R.id.txt);
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {

                name.setText("seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                name.setText("done!");
            }
        }.start();
    }
    FirebaseAuth firebaseAuth ;
    String str,ptr,a,strr,fin,gin;
    int temp_calc,count;
    public  void addClicked(View view)
    {
         firebaseDatabase = FirebaseDatabase.getInstance();
         databaseReference = firebaseDatabase.getReference().child("Buildings");
         databaseReference1 = firebaseDatabase.getReference().child("Number");
         databaseReferenceadmin = firebaseDatabase.getReference().child("History").child("Admin");
         databaseReferencehistory = firebaseDatabase.getReference().child("History").child("Usres");
         userss = firebaseDatabase.getReference().child("Users");
         firebaseAuth =  FirebaseAuth.getInstance();
         firebaseUser = firebaseAuth.getCurrentUser();
         strr = firebaseUser.getEmail();

        final Intent intent = getIntent();
         str = intent.getStringExtra("Buildi");
         ptr = intent.getStringExtra("num");
         temp_calc = Integer.parseInt(ptr);
         temp_calc = (temp_calc/10)*10;
         a= String.valueOf(temp_calc);
        userss.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.getValue().toString().compareTo(strr)==0)
                    {
                        fin=snapshot.getKey();
                        gin=snapshot.getKey();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        count=0;
         AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Final Confirmation")
                .setMessage("Confirm booking for room"+intent.getStringExtra("Div"))
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference.child(str).child(a).child(ptr).setValue(1);
                        databaseReferencehistory.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if (snapshot.getValue().toString().compareTo(strr) == 0)
                                    {
                                        fin = snapshot.getKey();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseReferenceadmin.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                {
                                    if (snapshot.getValue().toString().compareTo(strr) == 0)
                                    {
                                        gin = snapshot.getKey();
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        databaseReferencehistory.child(fin).push().child(str).child(ptr).setValue(1);
                        databaseReferenceadmin.child(gin).push().child(str).child(ptr).setValue(1);
                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        Intent intent1 = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent1);
                    }
                })
                .create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            private static final int AUTO_DISMISS_MILLIS = 6000;
            @Override
            public void onShow(final DialogInterface dialog) {
                final Button defaultButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                final CharSequence negativeButtonText = defaultButton.getText();
                new CountDownTimer(AUTO_DISMISS_MILLIS, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        defaultButton.setText(String.format(
                                Locale.getDefault(), "%s (%d)",
                                negativeButtonText,
                                TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) + 1 //add one so it never displays zero
                        ));
                    }
                    @Override
                    public void onFinish() {
                        if (((AlertDialog) dialog).isShowing()) {
                            dialog.dismiss();

                        }
                    }
                }.start();
             }
        });
        dialog.show();
        Toast.makeText(Timer.this,intent.getStringExtra("Div") , Toast.LENGTH_SHORT).show();
    }
}
