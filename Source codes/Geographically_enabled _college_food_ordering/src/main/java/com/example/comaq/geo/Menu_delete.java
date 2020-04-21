package com.example.comaq.geo;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Menu_delete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_delete);


        lsd = findViewById(R.id.firstview);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("menu");
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        lsd.setAdapter(arrayAdapter);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    arrayAdapter.add(snapshot.getKey());
                    arrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lsd.setClickable(true);
        lsd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Name = lsd.getItemAtPosition(position).toString();
            }
        });

    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    String Name, Price;
    int n;
    List<String> arrayList = new ArrayList<>();
    Spinner choice;
    ListView lsd;
    String ch;
    ArrayAdapter<String> arrayAdapter;

    public void delete(View view) {

        lsd = findViewById(R.id.firstview);
        Intent intent = new Intent(this, FinalDelete.class);
        intent.putExtra("Category", Name);
        startActivity(intent);

    }
}
