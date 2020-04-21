package com.example.comaq.geo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AdminPrivilages extends AppCompatActivity {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_privilages);
    }

    public void menuClicked(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);

    }

    public void allOrderClicked(View view) {
        Intent intent = new Intent(this, allOrder.class);
        startActivity(intent);
    }

}
