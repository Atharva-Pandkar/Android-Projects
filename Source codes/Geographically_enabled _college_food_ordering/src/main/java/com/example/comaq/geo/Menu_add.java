package com.example.comaq.geo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Menu_add extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_add);


    }

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, ref;
    String Name;
    String Price;
    Spinner spinner;
    String choice;

    public void AddClicked(View view) {
        spinner = findViewById(R.id.spinnerchoce);
        choice = spinner.getSelectedItem().toString();
        EditText name = findViewById(R.id.EditTextName);
        EditText price = findViewById(R.id.EditTextNumber);
        Name = name.getText().toString();
        Price = price.getText().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("menu").child(choice).child(Name).setValue(Price);
        Intent intent = new Intent(this, Menu_add.class);
        startActivity(intent);
    }

    public void back(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
