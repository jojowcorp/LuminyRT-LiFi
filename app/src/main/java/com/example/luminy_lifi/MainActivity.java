package com.example.luminy_lifi;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView listViewTodos;

    private ArrayList<String> ListOfTodos = new ArrayList<>();

    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR = 1.0f;
    ImageView imageView;

    FloatingActionButton addContactFab, addLocationFab, addSetting, addCalendar;
    ExtendedFloatingActionButton addActionFab;

    Boolean isALLFABVisible;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTodos = findViewById(R.id.ListViewTodos);

        addContactFab = findViewById(R.id.add_contact);
        addLocationFab = findViewById(R.id.add_location);
        addSetting = findViewById(R.id.add_setting);
        addCalendar = findViewById(R.id.add_calendar);

        addActionFab = findViewById(R.id.add_fab);

        addContactFab.setVisibility(View.GONE);
        addLocationFab.setVisibility(View.GONE);
        addSetting.setVisibility(View.GONE);
        addCalendar.setVisibility(View.GONE);

        isALLFABVisible = false;

        addActionFab.shrink();

        addLocationFab.setOnClickListener(v ->
        {
            scanCode();
        });

        addContactFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
            }
        });

        addActionFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isALLFABVisible){
                    addContactFab.show();
                    addLocationFab.show();
                    addSetting.show();
                    addCalendar.show();

                    addActionFab.extend();
                    isALLFABVisible = true;
                }
                else {
                    addContactFab.hide();
                    addLocationFab.hide();
                    addSetting.hide();
                    addCalendar.hide();

                    addActionFab.shrink();
                    isALLFABVisible = false;
                }
            }
        });
    }
        private void scanCode()
        {
            ScanOptions options = new ScanOptions();
            options.setPrompt("Augmenter le volume pour activer le flash");
            options.setBeepEnabled(true);
            options.setOrientationLocked(true);
            options.setCaptureActivity(CaptureAct.class);
            barLauncher.launch(options);
        }

        ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result -> {
            if (result.getContents() != null)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Result");
                builder.setMessage(result.getContents());
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i)
                    {
                        dialogInterface.dismiss();
                    }
                }).show();
            }
        });

        @Override
        public boolean onTouchEvent(MotionEvent event){
            scaleGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

        class ScaleListner extends ScaleGestureDetector.SimpleOnScaleGestureListener{
            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                FACTOR *= detector.getScaleFactor();
                FACTOR = Math.max(0.1f, Math.min(FACTOR, 10.f));
                imageView.setScaleX(FACTOR);
                imageView.setScaleY(FACTOR);
                return true;
            }
        }


}