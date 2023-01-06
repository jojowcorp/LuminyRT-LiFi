package com.example.luminy_lifi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity2 extends AppCompatActivity {

    ExtendedFloatingActionButton addRetourFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        addRetourFab = findViewById(R.id.btn_retour);
        addRetourFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}