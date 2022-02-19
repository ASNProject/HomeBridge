package com.example.homebridge;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

private TextView ntemperature, nday, ndate, statuslamp1, statuslamp2, statuslamp3, statuslamp4, statusfan, statustv, sjumlahkwh,
                shargaharian, shargabulanan, lamp1, lamp2, lamp3, lamp4, fan, tv;
private Button btnwhite1, btnred1, btnblue1, btngreen1, btnofflamp1,
        btnwhite2, btnred2, btnblue2, btngreen2, btnofflamp2,
        btnwhite3, btnred3, btnblue3, btngreen3, btnofflamp3,
        btnwhite4, btnred4, btnblue4, btngreen4, btnofflamp4,
        btnonfan1, btnonfan2, btnonfan3, btnofffan,
        btnontv, btnofftv,
        btnsavelamp1, btnsavelamp2, btnsavelamp3, btnsavelamp4, btnsavefan, btnsavetv;
private ToggleButton modelamp1, modelamp2, modelamp3, modelamp4, modefan, modetv;
private EditText ntimeronlamp1, ntimerofflamp1,
        ntimeronlamp2, ntimerofflamp2,
        ntimeronlamp3, ntimerofflamp3,
        ntimeronlamp4, ntimerofflamp4,
        ntimeronfan, ntimerofffan,
        ntimerontv, ntimerofftv;

private DatabaseReference database;
private SensorManager sensorManager;
private Sensor tempSensor;
private Boolean isTemperatureSensorAvailable;




    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = FirebaseDatabase.getInstance().getReference();
        FirebaseDatabase mdatabase = FirebaseDatabase.getInstance();


        ntemperature = (TextView) findViewById(R.id.temperature);
        nday = (TextView) findViewById(R.id.day);
        ndate = (TextView) findViewById(R.id.date);

        btnwhite1 = (Button) findViewById(R.id.white);
        btnred1 = (Button) findViewById(R.id.red);
        btnblue1 = (Button) findViewById(R.id.blue);
        btngreen1 = (Button) findViewById(R.id.green);
        btnofflamp1 = (Button) findViewById(R.id.btnofflamp);
        btnsavelamp1 = (Button) findViewById(R.id.savetimerlamp);
        btnwhite2 = (Button) findViewById(R.id.white1);
        btnred2 = (Button) findViewById(R.id.red1);
        btnblue2 = (Button) findViewById(R.id.blue1);
        btngreen2 = (Button) findViewById(R.id.green1);
        btnofflamp2 = (Button) findViewById(R.id.btnofflamp1);
        btnsavelamp2 = (Button) findViewById(R.id.savetimerlamp1);
        btnwhite3 = (Button) findViewById(R.id.white2);
        btnred3 = (Button) findViewById(R.id.red2);
        btnblue3 = (Button) findViewById(R.id.blue2);
        btngreen3 = (Button) findViewById(R.id.green2);
        btnofflamp3 = (Button) findViewById(R.id.btnofflamp2);
        btnsavelamp3 = (Button) findViewById(R.id.savetimerlamp2);
        btnwhite4 = (Button) findViewById(R.id.white3);
        btnred4 = (Button) findViewById(R.id.red3);
        btnblue4 = (Button) findViewById(R.id.blue3);
        btngreen4 = (Button) findViewById(R.id.green3);
        btnofflamp4 = (Button) findViewById(R.id.btnofflamp3);
        btnsavelamp4 = (Button) findViewById(R.id.savetimerlamp3);


        btnonfan1 = (Button) findViewById(R.id.fan1);
        btnonfan2 = (Button) findViewById(R.id.fan2);
        btnonfan3 = (Button) findViewById(R.id.fan3);
        btnofffan = (Button) findViewById(R.id.fanoff);
        btnsavefan = (Button) findViewById(R.id.savefan);

        btnontv = (Button) findViewById(R.id.ontv);
        btnofftv = (Button) findViewById(R.id.offtv);
        btnsavetv = (Button) findViewById(R.id.savetv);


        modelamp1 = (ToggleButton) findViewById(R.id.mode);
        modelamp2 = (ToggleButton) findViewById(R.id.modelamp1);
        modelamp3 = (ToggleButton) findViewById(R.id.modelamp2);
        modelamp4 = (ToggleButton) findViewById(R.id.modelamp3);
        modefan = (ToggleButton) findViewById(R.id.modefan);
        modetv = (ToggleButton) findViewById(R.id.modetv);


        ntimeronlamp1 = (EditText) findViewById(R.id.timeronlamp);
        ntimerofflamp1 = (EditText) findViewById(R.id.timerofflamp);
        ntimeronlamp2 = (EditText) findViewById(R.id.timeronlamp1);
        ntimerofflamp2 = (EditText) findViewById(R.id.timerofflamp1);
        ntimeronlamp3 = (EditText) findViewById(R.id.timeronlamp2);
        ntimerofflamp3 = (EditText) findViewById(R.id.timerofflamp2);
        ntimeronlamp4 = (EditText) findViewById(R.id.timeronlamp3);
        ntimerofflamp4 = (EditText) findViewById(R.id.timerofflamp3);
        ntimeronfan = (EditText) findViewById(R.id.timeronfan);
        ntimerofffan = (EditText) findViewById(R.id.timerofffan);
        ntimerontv = (EditText) findViewById(R.id.timerontv);
        ntimerofftv = (EditText) findViewById(R.id.timerofftv);

        //Status
        statuslamp1 = (TextView) findViewById(R.id.statuslamp1);
        statuslamp2 = (TextView) findViewById(R.id.statuslamp2);
        statuslamp3 = (TextView) findViewById(R.id.statuslamp3);
        statuslamp4 = (TextView) findViewById(R.id.statuslamp4);
        statusfan = (TextView) findViewById(R.id.statusfan);
        statustv = (TextView) findViewById(R.id.statustv);

        //Kalkulasi
        sjumlahkwh = (TextView) findViewById(R.id.jumlahkwh);
        shargaharian = (TextView) findViewById(R.id.hargaharian);
        shargabulanan = (TextView) findViewById(R.id.hargabulanan);
        lamp1 = (TextView) findViewById(R.id.lamp1);
        lamp2 = (TextView) findViewById(R.id.lamp2);
        lamp3 = (TextView) findViewById(R.id.lamp3);
        lamp4 = (TextView) findViewById(R.id.lamp4);
        fan = (TextView) findViewById(R.id.fan);
        tv = (TextView) findViewById(R.id.tv);

        //Sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null)
        {
            tempSensor = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
            isTemperatureSensorAvailable = true;
        }
        else {
            ntemperature.setText("0");
            isTemperatureSensorAvailable = false;
        }

        //Button LED 1
        btnwhite1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("1");
            }
        });
        btnred1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("2");
            }
        });
        btngreen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("3");
            }
        });
        btnblue1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("4");
            }
        });
        btnofflamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led").setValue("0");
            }
        });
        //Button LED 2
        btnwhite2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led2").setValue("1");
            }
        });
        btnred2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led2").setValue("2");
            }
        });
        btngreen2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led2").setValue("3");
            }
        });
        btnblue2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led2").setValue("4");
            }
        });
        btnofflamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led2").setValue("0");
            }
        });
        //Button LED 3
        btnwhite3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led3").setValue("1");
            }
        });
        btnred3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led3").setValue("2");
            }
        });
        btngreen3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led3").setValue("3");
            }
        });
        btnblue3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led3").setValue("4");
            }
        });
        btnofflamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led3").setValue("0");
            }
        });
        //Button LED 4
        btnwhite4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led4").setValue("1");
            }
        });
        btnred4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led4").setValue("2");
            }
        });
        btngreen4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led4").setValue("3");
            }
        });
        btnblue4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led4").setValue("4");
            }
        });
        btnofflamp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("led4").setValue("0");
            }
        });
        //Button FAN
        btnonfan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("1");
                database.child("relay2").setValue("0");
                database.child("relay3").setValue("0");
            }
        });
        btnonfan2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("0");
                database.child("relay2").setValue("1");
                database.child("relay3").setValue("0");
            }
        });
        btnonfan3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("0");
                database.child("relay2").setValue("0");
                database.child("relay3").setValue("1");
            }
        });
        btnofffan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay1").setValue("0");
                database.child("relay2").setValue("0");
                database.child("relay3").setValue("0");
            }
        });
        //Button TV
        btnontv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay4").setValue("1");
            }
        });
        btnofftv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.child("relay4").setValue("0");
            }
        });

        //TIMER VIEW
        //Timer LED 1
        mdatabase.getReference("timeronlamp1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimeronlamp1.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timerofflamp1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofflamp1.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Timer LED 2
        mdatabase.getReference("timeronlamp2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimeronlamp2.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timerofflamp2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofflamp2.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Timer LED 3
        mdatabase.getReference("timeronlamp3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimeronlamp3.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timerofflamp3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofflamp3.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Timer LED 4
        mdatabase.getReference("timeronlamp4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimeronlamp4.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timerofflamp4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofflamp4.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Timer FAN
        mdatabase.getReference("timerfanon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimeronfan.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timerfanoff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofffan.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Timer TV
        mdatabase.getReference("timertvon").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerontv.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("timertvoff").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ntimerofftv.setText(snapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        //Status
        mdatabase.getReference("modelamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statuslamp1.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("modelamp2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statuslamp2.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("modelamp3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statuslamp3.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("modelamp4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statuslamp4.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("modefan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statusfan.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("modetv").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                statustv.setText(snapshot.getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //Toggle Button
        btnsavelamp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelamp1.isChecked()){
                    database.child("modelamp").setValue("Timer");
                    database.child("led").setValue("1");
                }
                else{
                    database.child("modelamp").setValue("Manual");
                    database.child("led").setValue("0");
                }
                database.child("timeronlamp1").setValue(ntimeronlamp1.getText().toString());
                database.child("timerofflamp1").setValue(ntimerofflamp1.getText().toString());
            }
        });
        btnsavelamp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelamp2.isChecked()){
                    database.child("modelamp2").setValue("Timer");
                    database.child("led2").setValue("1");
                }
                else{
                    database.child("modelamp2").setValue("Manual");
                    database.child("led2").setValue("0");
                }
                database.child("timeronlamp2").setValue(ntimeronlamp2.getText().toString());
                database.child("timerofflamp2").setValue(ntimerofflamp2.getText().toString());
            }
        });
        btnsavelamp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelamp3.isChecked()){
                    database.child("modelamp3").setValue("Timer");
                    database.child("led3").setValue("1");
                }
                else{
                    database.child("modelamp3").setValue("Manual");
                    database.child("led3").setValue("0");
                }
                database.child("timeronlamp3").setValue(ntimeronlamp3.getText().toString());
                database.child("timerofflamp3").setValue(ntimerofflamp3.getText().toString());
            }
        });
        btnsavelamp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modelamp4.isChecked()){
                    database.child("modelamp4").setValue("Timer");
                    database.child("led4").setValue("1");
                }
                else{
                    database.child("modelamp4").setValue("Manual");
                    database.child("led4").setValue("0");
                }
                database.child("timeronlamp4").setValue(ntimeronlamp4.getText().toString());
                database.child("timerofflamp4").setValue(ntimerofflamp4.getText().toString());
            }
        });
        btnsavefan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modefan.isChecked()){
                    database.child("modefan").setValue("Timer");
                    database.child("relay1").setValue("1");
                }
                else{
                    database.child("modefan").setValue("Manual");
                    database.child("relay1").setValue("0");
                }
                database.child("timerfanon").setValue(ntimeronfan.getText().toString());
                database.child("timerfanoff").setValue(ntimerofffan.getText().toString());
            }
        });
        btnsavetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (modetv.isChecked()){
                    database.child("modetv").setValue("Timer");
                }
                else{
                    database.child("modetv").setValue("Manual");
                }
                database.child("timertvon").setValue(ntimerontv.getText().toString());
                database.child("timertvoff").setValue(ntimerofftv.getText().toString());
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

        //Perhitungan Kalkulasi

        mdatabase.getReference("kwh").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double nkawh = snapshot.getValue(Double.class);
                String a = String.valueOf(String.format("%.3f", nkawh));
                sjumlahkwh.setText(a + " " + "kWh");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("biayaharian").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double harian = snapshot.getValue(Double.class);
                String a = String.valueOf(String.format("%.2f", harian));
                shargaharian.setText("Rp." + "" + a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mdatabase.getReference("biayabulanan").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Double bulanan = snapshot.getValue(Double.class);
                String a = String.valueOf(String.format("%.2f", bulanan));
                shargabulanan.setText("Rp." + "" + a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mdatabase.getReference("temperature").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer temp = snapshot.getValue(Integer.class);
                String a = String.valueOf(temp);
                ntemperature.setText(a+"°C");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
     //   ntemperature.setText(sensorEvent.values[0]+"°C");

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(isTemperatureSensorAvailable){
           // sensorManager.registerListener(this, tempSensor,SensorManager.SENSOR_DELAY_NORMAL);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if(isTemperatureSensorAvailable){
            sensorManager.unregisterListener(this);
        }
    }
}