package fr.luminy_lifi.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    public static MainActivity3 instance;



    ExtendedFloatingActionButton addBtn_retour;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd'T'HHmmss'Z'");
    static ZoneId zoneId = ZoneId.of("Europe/Paris");
    static Duration duration = Duration.ofMinutes(10);
    static ZonedDateTime now = ZonedDateTime.now(zoneId);
    static String CalendarToDisplay = "";
    static String CalendarUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        instance = this;

        ((TextView)instance.findViewById(R.id.url_calendar_info)).setText(CalendarToDisplay);
        ((EditText)findViewById(R.id.url_calendar)).setText(CalendarUrl);


        ((EditText)findViewById(R.id.url_calendar)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String url = ((EditText)findViewById(R.id.url_calendar)).getText().toString();
                if(!url.replaceAll(" ","").equals("")) {
                    new downloadCalendar().execute(url);
                } else {
                    ((TextView)instance.findViewById(R.id.url_calendar_info)).setText("Aucun calendrier enregistré");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        addBtn_retour = findViewById(R.id.btn_retour_3);

        addBtn_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    static class downloadCalendar extends AsyncTask<String, Void, Calendar> {

        @Override
        protected Calendar doInBackground(String... strings) {
            Calendar cal = null;
            InputStream is = null;
            try {
                is = new URL(strings[0]).openStream();
                CalendarUrl = strings[0];

                cal = new CalendarBuilder().build(is);
                SharedPreferences sharedPreferences = MainActivity.instance.getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("url_calendar", strings[0]);
                editor.commit();
            }catch(Exception ignore) {}
            return cal;
        }

        @Override
        protected void onPostExecute(Calendar result) {
            if(result == null) {
                CalendarToDisplay = "Impossible de charger le calendrier ...";
            } else {
                CalendarToDisplay = "Calendrier chargé! "+result.getVersion().toString();
                MainActivity.instance.updateCalendar(result);

            }

        }
    }
}