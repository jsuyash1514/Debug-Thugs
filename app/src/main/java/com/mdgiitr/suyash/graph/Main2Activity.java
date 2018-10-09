package com.mdgiitr.suyash.graph;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mdgiitr.suyash.graph.R;
import com.mdgiitr.suyash.graphkit.DataPoint;

import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

public class Main2Activity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;

    private TextView textView;
    private ListView listView;
    private Button btn_showList;
    private Button btn_resetValues;
    private Button btn_showGraph;
    private ArrayAdapter<String> arrayAdapter;

    private long startingTime;

    public static ArrayList<DataPoint> dataPointsArrayList;

    private float az1, az2, vz1, vz2, z1, z2;
    private long prevTime, newTime;
    //fail below is only a hint to the system. Events may be received faster or slower than the specified rate. Usually events are received faster.
    private int SENSOR_SAMPLING_PERIOD = 1; //in milliseconds
    private float SENSOR_SAMPLING_PERIOD_inSeconds = 0.001F;

    private ArrayList<String> listZ;
    private ArrayList<String> listToShow;

    private int ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        startingTime=System.currentTimeMillis();

        textView = (TextView) findViewById(R.id.id_textView);
        btn_showList = (Button) findViewById(R.id.id_btn_showValues);
        btn_showGraph=(Button)findViewById(R.id.id_btn_showGraph);
        listView = (ListView) findViewById(R.id.id_listView);
        btn_resetValues = (Button) findViewById(R.id.id_btnResetValues);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorManager.registerListener(Main2Activity.this, accelerometer, SENSOR_SAMPLING_PERIOD * 1000);

        listZ = new ArrayList<String>();
        listToShow = new ArrayList<String>();

        dataPointsArrayList=new ArrayList<>();

        arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                listZ);
        listView.setAdapter(arrayAdapter);

        btn_showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main2Activity.this,GraphOFz.class));
                Toast.makeText(Main2Activity.this, "TESTING", Toast.LENGTH_SHORT).show();
            }
        });

        btn_showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                fillListToShow();
                arrayAdapter.notifyDataSetChanged();
            }
        });

        btn_resetValues.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                az1 = az2 = vz1 = vz2 = z1 = z2 = 0;
                listZ.clear();
                listToShow.clear();
                arrayAdapter.notifyDataSetChanged();
                startingTime=newTime;
                dataPointsArrayList.clear();
            }
        });
    }

//    public void fillListToShow() {
//        Iterator<String> iterator = listZ.iterator();
//        while (iterator.hasNext()) {
//            for (int i = 0; i <= 0; i++) {
//                if (iterator.hasNext()) {
//                    iterator.next();
//                }
//            }
//            if (iterator.hasNext()) {
//                listToShow.add(iterator.next());
//            }
//        }
//    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText("X : " + event.values[0] + "\nY : " + event.values[1] + "\nZ : " + event.values[2]);

        newTime = System.currentTimeMillis();
        if (newTime - prevTime >= SENSOR_SAMPLING_PERIOD) {
            if (Math.abs(event.values[2]) > 0.5) {
                az2 = event.values[2];
                vz2 = vz1 + ((az2 + az1) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds;
                z2 = z1 + (((vz1 + vz2) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds) + ((1 / 2) * ((az1 + az2) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds * SENSOR_SAMPLING_PERIOD_inSeconds);
                listZ.add("Z = " + z2 + " V = " + vz2);
                az1 = az2;
                vz1 = vz2;
                z1 = z2;

                dataPointsArrayList.add(new DataPoint((float) (newTime-startingTime)/10,(z2*10+5)));
            } else {
                ct++;
                if (ct >= 2) {
                    az1 = az2 = vz1 = vz2 = ct = 0;
                }
            }
        }
        prevTime = newTime;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}