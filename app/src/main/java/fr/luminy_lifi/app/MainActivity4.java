package fr.luminy_lifi.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class MainActivity4 extends AppCompatActivity {

    Switch switcher;
    ExtendedFloatingActionButton btn_retour4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        getSupportActionBar().hide();

        switcher = findViewById(R.id.switcher);
        btn_retour4 = findViewById(R.id.btn_retour4);

        //Permet de sauvegarder les preferences en cas d'arret par l'utilisateur de l'appli


        switcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.instance.setSombre(((Switch)v).isActivated());
            }
        });


        btn_retour4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity4.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}