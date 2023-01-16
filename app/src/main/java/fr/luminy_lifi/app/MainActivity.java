package fr.luminy_lifi.app;

import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;

import fr.luminy_lifi.app.zoom.ZoomageView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    public dataManager data;
    public static MainActivity instance;
    public int UserAtId= 0;

    public List<dataManager.Point> path = null;



    private ScaleGestureDetector scaleGestureDetector;
    private float FACTOR = 1.0f;
    ImageView imageView;

    FloatingActionButton addContactFab, addLocationFab, addSetting, addCalendar;
    ExtendedFloatingActionButton addActionFab;

    Boolean isALLFABVisible;

    private Calendar cal = null;

    private SensorManager mSensorManager;
    private Sensor mLight;


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
            if(searchPointId != -1) {
                if(searchPointId == UserAtId) {
                    path = null;
                } else {
                    instance.path = PathUtils.findPathTo(p,data.getPointById(searchPointId));
                }
            }

        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.instance);
            builder.setTitle("Warn")
                    .setMessage("Position id "+id+" incorrect.")
                    .setPositiveButton("OK", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }

    }

    private int btnId1 = -1,btnId2 = -1,btnId3 = -1;

    private boolean isSombre = false;
    public SharedPreferences sharedPrefSombre;
    SharedPreferences.Editor editor;

    public void setSombre(boolean v) {
        this.isSombre = v;
        AppCompatDelegate.setDefaultNightMode(v ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES);
        editor = sharedPrefSombre.edit();
        editor.putBoolean("sombre", v);
        editor.apply();
    }

    public boolean isSombre() {
        return this.isSombre;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;


        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        // Obtenir une référence au capteur de luminosité
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        sharedPrefSombre = getSharedPreferences("Mode", Context.MODE_PRIVATE);
        // Le mode par default est le thème de jour
        boolean nightMode = sharedPrefSombre.getBoolean("sombre", false);
        this.isSombre = nightMode;
        if (isSombre) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            editor = sharedPrefSombre.edit();
            editor.putBoolean("sombre", true);
            editor.apply();
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            editor = sharedPrefSombre.edit();
            editor.putBoolean("sombre", false);
            editor.apply();
        }




        setContentView(R.layout.activity_main);


        findViewById(R.id.search_bar_todos).setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                findViewById(R.id.searchBarLayout).setVisibility(hasFocus ? View.VISIBLE : View.GONE);
            }
        });

        //

        /*AppCompatDelegate.setDefaultNightMode(MainActivity4.);*/

        //
        /*findViewById(R.id.zoomable).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((EditText)findViewById(R.id.search_bar_todos)).clearFocus();
                if(findViewById(R.id.searchBarLayout).getVisibility() == View.VISIBLE)  {
                    findViewById(R.id.searchBarLayout).setVisibility(View.GONE);
                }
            }
        });*/
        ((EditText)findViewById(R.id.search_bar_todos)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i0, int i1, int i2) {
                if(data != null) {
                    List<dataManager.Point> point = data.getPoints();

                    List<dataManager.LocationPoint> toShow = new ArrayList<dataManager.LocationPoint>();
                    String txt = ((EditText)findViewById(R.id.search_bar_todos)).getText().toString();

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
                    findViewById(R.id.searchBarLayout).setVisibility(View.VISIBLE);
                    findViewById(R.id.search1).setVisibility(View.GONE);
                    findViewById(R.id.search2).setVisibility(View.GONE);
                    findViewById(R.id.search3).setVisibility(View.GONE);
                    btnId1 = btnId2 = btnId3 = -1;
                    if(!toShow.isEmpty()) {
                        int i = 0;
                        for(dataManager.LocationPoint p:toShow) {
                            if(i == 0) {
                                findViewById(R.id.search1).setBackgroundColor(Color.rgb(238,238,238));
                                findViewById(R.id.search1).setVisibility(View.VISIBLE);

                                ((Button)findViewById(R.id.search1)).setText(p.Name);
                                btnId1 = p.ID;
                                i++;
                            } else if(i == 1) {
                                findViewById(R.id.search2).setVisibility(View.VISIBLE);
                                findViewById(R.id.search2).setBackgroundColor(Color.rgb(238,238,238));
                                ((Button)findViewById(R.id.search2)).setText(p.Name);
                                btnId2 = p.ID;
                                i++;
                            } else if(i == 2) {
                                findViewById(R.id.search3).setVisibility(View.VISIBLE);
                                //findViewById(R.id.search3).setBackgroundResource(R.drawable.stylbutton);
                                findViewById(R.id.search3).setBackgroundColor(Color.rgb(238,238,238));
                                ((Button)findViewById(R.id.search3)).setText(p.Name);
                                btnId3 = p.ID;
                                break;
                            }
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        findViewById(R.id.search1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnSearch(btnId1);
            }
        });
        findViewById(R.id.search2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnSearch(btnId2);
            }
        });
        findViewById(R.id.search3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickOnSearch(btnId3);
            }
        });




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

        addCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });

        addSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity4.class);
                startActivity(intent);
                finish();
            }
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
    private int searchPointId = -1;
    public void clickOnSearch(int id) {
        if(id != -1) {
            findViewById(R.id.search1).setVisibility(View.GONE);
            findViewById(R.id.search2).setVisibility(View.GONE);
            findViewById(R.id.search3).setVisibility(View.GONE);
            ((EditText)findViewById(R.id.search_bar_todos)).setText("");
            findViewById(R.id.searchBarLayout).setVisibility(View.GONE);
            dataManager.Point p1 = data.getPointById(instance.UserAtId);
            dataManager.Point p2 = data.getPointById(id);
            if(p1 != null && p2 != null) {
                searchPointId = id;
                instance.path = PathUtils.findPathTo(p1, p2);
                instance.defineNewUserAt(instance.UserAtId);
            } else {
                Log.i("ERROR POINT", "P1:"+p1+" ID:"+instance.UserAtId+" | P2:"+p2+" ID:"+id);
            }
        }
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
                try {
                    int id = Integer.parseInt(result.getContents());
                    defineNewUserAt(id);
                } catch(Exception e) {}
            }
        });

        @Override
        public boolean onTouchEvent(MotionEvent event){
            scaleGestureDetector.onTouchEvent(event);
            return super.onTouchEvent(event);
        }

    private static final int THRESHOLD = 50; // seuil de luminosité pour détecter un signal
    private static final int PULSE_WIDTH = 50; // largeur d'impulsion pour détecter un signal

    private long pulseStart = 0; // timestamp de début de l'impulsion
    private long pulseEnd = 0; // timestamp de fin de l'impulsion

    @Override
    public final void onSensorChanged(SensorEvent event) {
        // Lire les données du capteur de luminosité
        float light = event.values[0];
        long timestamp = event.timestamp;

        // Traiter les données Li-Fi
        if (light > THRESHOLD) {
            // Luminosité supérieure au seuil, donc début d'une impulsion
            pulseStart = timestamp;
        } else {
            // Luminosité inférieure au seuil, donc fin d'une impulsion
            pulseEnd = timestamp;

            // Calculer la durée de l'impulsion
            long pulseDuration = pulseEnd - pulseStart;

            if (pulseDuration > PULSE_WIDTH) {
                // Durée d'impulsion supérieure à la largeur d'impulsion, donc signal de "1"
                // Traitement pour signal de "1" ici
                Log.i("LI-FI", "Pule (nano): "+pulseDuration);
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        // Enregistrer cette activité pour recevoir des mises à jour de l'événement
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Désactiver les mises à jour de l'événement
        mSensorManager.unregisterListener(this);
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

    public void updateCalendar(Calendar cal) {
            this.cal = cal;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
        ZoneId zoneId = ZoneId.of("Europe/Paris");
        Duration duration = Duration.ofMinutes(10);
        ZonedDateTime now = ZonedDateTime.now(zoneId);
        dataManager.LocationPoint p = null;
        for (Component component : cal.getComponents()) {
            if (component.getName().equals("VEVENT")) {
                Property start = component.getProperty("DTSTART");
                Property location = component.getProperty("LOCATION");

                LocalDateTime dateTime = LocalDateTime.parse(start.getValue(), formatter);
                ZonedDateTime eventStart = ZonedDateTime.of(dateTime, zoneId);

                if(eventStart.isAfter(now.minus(duration)) && eventStart.isBefore(now.plus(duration))) {
                    for(dataManager.Point p3 :data.getPoints()) {
                        if(p3 instanceof dataManager.LocationPoint) {
                            String name = ((dataManager.LocationPoint)p).Name;
                            System.out.println("Compare: "+name+" "+location.getValue()+" "+name.toLowerCase().contains(location.getValue().toLowerCase()));
                            if(name.toLowerCase().contains(location.getValue().toLowerCase())) {
                                p = (dataManager.LocationPoint) p3;
                                break;
                            }
                        }
                    }

                }

            }
        }
        if(p != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Emploie du temps");
            builder.setMessage("Vous avez cours maintenant en "+p.Name+", voulez-vous vous y rendre ?");
            dataManager.LocationPoint finalP = p;
            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dataManager.Point p1 = data.getPointById(instance.UserAtId);
                    instance.path = PathUtils.findPathTo(p1, finalP);
                    instance.defineNewUserAt(instance.UserAtId);
                }
            });
            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }


}