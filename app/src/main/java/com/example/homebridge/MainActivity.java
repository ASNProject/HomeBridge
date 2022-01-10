package com.example.homebridge;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

private TextView ntemperature, nday, ndate;
private Button btnwhite, btnred, btnblue, btngreen, btnofflamp, btnonfan, btnofffan, btnontv, btnofftv, btnonrefrigator, btnoffrefrigator, btnonrelay, btnoffrelay, btnsavelamp, btnsavefan, btnsavetv, btnsaverefigator, btnsaverelay;
private ToggleButton modelamp, modefan, modetv, moderefrigrator,moderelay;
private EditText ntimeronlamp, ntimerofflamp, ntimeronfan, ntimerofffan, ntimeronrefrigator, ntimeroffrefrigator, ntimerontv, ntimerofftv, ntimeronrelay, ntimeroffrelay;

private DatabaseReference database;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();

        ntemperature = (TextView) findViewById(R.id.temperature);
        nday = (TextView) findViewById(R.id.day);
        ndate = (TextView) findViewById(R.id.date);

        btnwhite = (Button) findViewById(R.id.white);
        btnred = (Button) findViewById(R.id.red);
        btnblue = (Button) findViewById(R.id.blue);
        btngreen = (Button) findViewById(R.id.green);
        btnofflamp = (Button) findViewById(R.id.btnofflamp);
        btnsavelamp = (Button) findViewById(R.id.savetimerlamp);
        btnonfan = (Button) findViewById(R.id.onfan);
        btnofffan = (Button) findViewById(R.id.offfan);
        btnsavefan = (Button) findViewById(R.id.savefan);
        btnontv = (Button) findViewById(R.id.ontv);
        btnofftv = (Button) findViewById(R.id.offtv);
        btnsavetv = (Button) findViewById(R.id.savetv);
        btnonrefrigator = (Button) findViewById(R.id.onref);
        btnoffrefrigator = (Button) findViewById(R.id.offref);
        btnsaverefigator = (Button) findViewById(R.id.saveref);
        btnonrelay = (Button) findViewById(R.id.onrelay);
        btnoffrelay = (Button) findViewById(R.id.offrelay);
        btnsaverelay = (Button) findViewById(R.id.saverelay);

        modelamp = (ToggleButton) findViewById(R.id.mode);
        modefan = (ToggleButton) findViewById(R.id.modefan);
        modetv = (ToggleButton) findViewById(R.id.modetv);
        moderefrigrator = (ToggleButton) findViewById(R.id.moderef);
        moderelay = (ToggleButton) findViewById(R.id.moderelay);

        ntimeronlamp = (EditText) findViewById(R.id.timeronlamp);
        ntimerofflamp = (EditText) findViewById(R.id.timerofflamp);
        ntimeronfan = (EditText) findViewById(R.id.timeronfan);
        ntimerontv = (EditText) findViewById(R.id.timerontv);
        ntimerofftv = (EditText) findViewById(R.id.timerofftv);
        ntimeronrefrigator = (EditText) findViewById(R.id.timeronref);
        ntimeroffrefrigator = (EditText) findViewById(R.id.timeroffref);
        ntimeronrelay = (EditText) findViewById(R.id.timeronrelay);
        ntimeroffrelay = (EditText) findViewById(R.id.timeroffrelay);

        btnwhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("1");
            }
        });
        btnred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("2");
            }
        });
        btngreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("3");
            }
        });
        btnblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("4");
            }
        });
        btnofflamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("0");
            }
        });
        btnonfan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("1");
            }
        });
        btnofffan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("0");
            }
        });
        btnontv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay2").setValue("1");
            }
        });
        btnofftv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay2").setValue("0");
            }
        });
        btnonrefrigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay3").setValue("1");
            }
        });
        btnoffrefrigator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay3").setValue("0");
            }
        });
        btnonrelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay4").setValue("1");
            }
        });
        btnoffrelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay4").setValue("0");
            }
        });

        //Get Time
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMMM yyyy");
        String date = simpleDateFormat.format(calendar.getTime());
        ndate.setText(date);

        //Get day
        LocalDate date1 = LocalDate.now();
        DayOfWeek dow = date1.getDayOfWeek();
        String dayname = dow.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
        nday.setText(dayname);







    }
}