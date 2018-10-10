package com.mdgiitr.debugThugs.graph;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    public static LineGraphSeries<DataPoint> series;
    private SensorManager sensorManager;
    private Sensor accelerometer;
    private TextView textView;
    private ListView listView;
    private Button btn_showList;
    private Button btn_resetValues;
    private Button btn_showGraph;
    private ArrayAdapter<String> arrayAdapter;
    private long startingTime;
    private float az1, az2, vz1, vz2, z1, z2;
    private long prevTime, newTime;
    private int SENSOR_SAMPLING_PERIOD = 1; //in milliseconds
    private float SENSOR_SAMPLING_PERIOD_inSeconds = 0.001F;
    private ArrayList<String> listForListView;
//    private ArrayList<String> listToShow;
    private int ct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startingTime = System.currentTimeMillis();

        textView = (TextView) findViewById(R.id.id_textView);
        btn_showList = (Button) findViewById(R.id.id_btn_showValues);
        btn_showGraph = (Button) findViewById(R.id.id_btn_showGraph);
        listView = (ListView) findViewById(R.id.id_listView);
        btn_resetValues = (Button) findViewById(R.id.id_btnResetValues);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        sensorManager.registerListener(MainActivity.this, accelerometer, SENSOR_SAMPLING_PERIOD * 1000);

        listForListView = new ArrayList<String>();
//        listToShow = new ArrayList<String>();


        series = new LineGraphSeries<>();

        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                listForListView);
        listView.setAdapter(arrayAdapter);

        btn_showGraph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GraphOfZ.class));
                Toast.makeText(MainActivity.this, "TESTING", Toast.LENGTH_SHORT).show();
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
                series = new LineGraphSeries<>();
                az1 = az2 = vz1 = vz2 = z1 = z2 = 0;
                listForListView.clear();
//                listToShow.clear();
                arrayAdapter.notifyDataSetChanged();
                startingTime = newTime;
            }
        });
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        textView.setText("X : " + event.values[0] + "\nY : " + event.values[1] + "\nZ : " + event.values[2]);

        newTime = System.currentTimeMillis();
        if (newTime - prevTime >= SENSOR_SAMPLING_PERIOD) {
            if (Math.abs(event.values[2]) > 0.5) {
                az2 = event.values[2];
                vz2 = vz1 + ((az2 + az1) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds;
                z2 = z1 + (((vz1 + vz2) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds) + ((1 / 2) * ((az1 + az2) / 2) * SENSOR_SAMPLING_PERIOD_inSeconds * SENSOR_SAMPLING_PERIOD_inSeconds);
                listForListView.add("Z = " + z2 + " V = " + vz2);
                az1 = az2;
                vz1 = vz2;
                z1 = z2;

                series.appendData(new com.jjoe64.graphview.series.DataPoint((double) (newTime - startingTime) / 1000, (double) (z2 * 100)), true, 1000000000, false);


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
