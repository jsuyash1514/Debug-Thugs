package com.mdgiitr.suyash.graph;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mdgiitr.suyash.graphkit.LineGraph;

public class GraphOFz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_ofz);

        LineGraph lineGraph = (LineGraph)findViewById(R.id.id_lineGraph);
        lineGraph.setPoints(Main2Activity.dataPointsArrayList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }
}
