package com.example.comaq.geo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
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

public class FinalDelete extends AppCompatActivity {

    String category;
    ListView lsd;
    List<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_delete);

        category = getIntent().getExtras().getString("Category");
        lsd = findViewById(R.id.InnerList);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("menu").child(category);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    arrayAdapter.add(snapshot.getKey());
                    arrayAdapter.notifyDataSetChanged();
                }
                lsd.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        lsd.setClickable(true);
        lsd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            if (lsd.getItemAtPosition(position).toString().compareTo(snapshot.getKey()) == 0) {
                                databaseReference.child(lsd.getItemAtPosition(position).toString()).removeValue();

                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });
        lsd.setClickable(false);

    }

    public void deleteClicked(View view) {

        Intent intent = new Intent(this, AdminPrivilages.class);
        startActivity(intent);

    }
}
