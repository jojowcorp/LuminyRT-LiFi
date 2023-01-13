package fr.luminy_lifi.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import fr.luminy_lifi.app.zoom.ZoomageView;

public class MainActivity extends AppCompatActivity {


    public dataManager data;
    public static MainActivity instance;
    public int UserAtId= 0;



    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR = 1.0f;
    ImageView imageView;

    FloatingActionButton addContactFab, addLocationFab, addSetting, addCalendar;
    ExtendedFloatingActionButton addActionFab;

    Boolean isALLFABVisible;


    public void defineNewUserAt(int id) {
        dataManager.Point p = this.data.getPointById(id);
        if( p != null) {
            this.UserAtId = id;
            ZoomageView view = findViewById(R.id.zoomable);
            view.doubleTapDetected = true;

            long downTime = SystemClock.uptimeMillis();
            MotionEvent event = MotionEvent.obtain(
                    downTime,
                    downTime,
                    MotionEvent.ACTION_DOWN,
                    p.X,
                    p.Y,
                    0
            );
            view.longAnnim = true;
            view.onTouchEvent(event);

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
            builder.setTitle("Warn")
                    .setMessage("Position id "+id+" incorrect.")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;

        setContentView(R.layout.activity_main);

        findViewById(R.id.search_bar_todos).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findViewById(R.id.searchBarLayout).setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });
        findViewById(R.id.search_bar_todos).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(data != null) {
                    Log.i("OUIIIIIII", "keyinput "+keyCode);
                    List<dataManager.Point> point = data.getPoints();

                    List<dataManager.LocationPoint> toShow = new ArrayList<dataManager.LocationPoint>();
                    String txt = ((EditText)v).getText().toString();

                    if(!point.isEmpty()) {
                         if(!txt.replaceAll(" ", "").equals("")) {
                            for (dataManager.Point p : point) {
                                if (p instanceof dataManager.LocationPoint) {
                                    if (((dataManager.LocationPoint) p).Name.toLowerCase().contains(txt.toLowerCase())) {
                                        toShow.add(((dataManager.LocationPoint) p));
                                    }
                                }
                            }
                        }

                    }
                    Log.i("OUIIIIIII", txt +" - "+toShow.toString());

                    findViewById(R.id.search1).setVisibility(View.GONE);
                    findViewById(R.id.search2).setVisibility(View.GONE);
                    findViewById(R.id.search3).setVisibility(View.GONE);
                    if(!toShow.isEmpty()) {
                        int i = 0;
                        for(dataManager.LocationPoint p:toShow) {
                            if(i == 0) {
                                findViewById(R.id.search1).setBackgroundColor(Color.rgb(238,238,238));
                                findViewById(R.id.search1).setVisibility(View.VISIBLE);

                                ((Button)findViewById(R.id.search1)).setText(p.Name);
                                i++;
                            } else if(i == 1) {
                                findViewById(R.id.search2).setVisibility(View.VISIBLE);
                                findViewById(R.id.search2).setBackgroundColor(Color.rgb(238,238,238));
                                ((Button)findViewById(R.id.search2)).setText(p.Name);
                                i++;
                            } else if(i == 2) {
                                findViewById(R.id.search3).setVisibility(View.VISIBLE);
                                //findViewById(R.id.search3).setBackgroundResource(R.drawable.stylbutton);
                                findViewById(R.id.search3).setBackgroundColor(Color.rgb(238,238,238));
                                ((Button)findViewById(R.id.search3)).setText(p.Name);
                                break;
                            }
                        }
                    }
                }
                return true;
            }
        });



     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });*/


/////////////////////////////////////////

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

        this.data = new dataManager();
        this.data.init();

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