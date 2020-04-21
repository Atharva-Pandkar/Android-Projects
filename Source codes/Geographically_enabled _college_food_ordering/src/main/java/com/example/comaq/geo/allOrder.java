package com.example.comaq.geo;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class allOrder extends AppCompatActivity {
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseDatabase firebaseDatabase;
    String User, Name, Quantity, one = "", Price;
    DatabaseReference databaseReference,databaseReference2,databaseReference1;
    ListView lsd;
    Vector vc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);

        vc = new Vector();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Orders");
        databaseReference1 = firebaseDatabase.getReference("Account Details");
        databaseReference2 = firebaseDatabase.getReference().child("Past Orders");
        lsd = findViewById(R.id.lstall);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arrayList);
        lsd.setAdapter(arrayAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User = snapshot.getKey();

                    vc.add(User);
                    one = "Name = " + User;
                    int count = 1;
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        one = one + "\nItem No :-" + count;
                        count++;
                        for (DataSnapshot snapshot2 : snapshot1.getChildren())
                            one = one + "\n \t \t" + snapshot2.getKey() + "= " + snapshot2.getValue();
                    }
                    arrayAdapter.add(one);
                    arrayAdapter.notifyDataSetChanged();
                    one = "";

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lsd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                android.app.AlertDialog.Builder dia = new android.app.AlertDialog.Builder(allOrder.this);
                dia.setTitle("Confirm Order");
                dia.setMessage("");
                dia.setCancelable(false);
                dia.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    if ((snapshot.child("Email").getValue().toString()).compareTo(User) == 0) {
                                        Name = snapshot.getKey();
                                        databaseReference.child(Name).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                               databaseReference2.child(Name).push().setValue(dataSnapshot.getValue());
                                                databaseReference.child(Name).setValue(null);

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                });
                dia.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                    }
                });
                dia.create().show();

            }
        });
        lsd.refreshDrawableState();
        arrayAdapter.notifyDataSetChanged();


    }
}