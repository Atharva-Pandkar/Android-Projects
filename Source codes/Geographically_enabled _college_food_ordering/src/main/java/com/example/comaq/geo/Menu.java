package com.example.comaq.geo;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

    }

    public void addClicked(View view) {
        Intent intent = new Intent(this, Menu_add.class);
        startActivity(intent);

    }

    public void deleteClicked(View view) {
        Intent intent = new Intent(this, Menu_delete.class);
        startActivity(intent);
    }
}
