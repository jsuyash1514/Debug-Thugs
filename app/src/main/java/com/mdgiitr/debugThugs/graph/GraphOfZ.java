package com.mdgiitr.debugThugs.graph;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;

public class GraphOfZ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_of_z);

        GraphView graph = (GraphView) findViewById(R.id.graph);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-5);
        graph.getViewport().setMaxY(5);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(MainActivity.series);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }
}
