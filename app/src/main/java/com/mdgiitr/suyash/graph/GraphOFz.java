package com.mdgiitr.suyash.graph;

import android.graphics.DashPathEffect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.androidplot.util.PixelUtils;
import com.androidplot.xy.CatmullRomInterpolator;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.XYPlot;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mdgiitr.suyash.graphkit.LineGraph;

public class GraphOFz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph_ofz);

//        XYPlot plot = (XYPlot) findViewById(R.id.plot);
//
//
//        LineAndPointFormatter series1Format =
//                new LineAndPointFormatter(this, R.xml.line_point_formatter_with_labels);
//
//
//
//        series1Format.setInterpolationParams(
//                new CatmullRomInterpolator.Params(10, CatmullRomInterpolator.Type.Centripetal));
//

//        plot.addSeries(series1, series1Format);
//        plot.addSeries(series2, series2Format);

//        plot.addSeries(Main2Activity.series, series1Format);
//
//        plot.getGraph();


        GraphView graph = (GraphView) findViewById(R.id.graph);


        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMinY(-5);
        graph.getViewport().setMaxY(5);

        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setMinX(0);

        // enable scaling and scrolling
        graph.getViewport().setScalable(true);
        graph.getViewport().setScalableY(true);

        graph.addSeries(Main2Activity.series);


//        LineGraph lineGraph = (LineGraph)findViewById(R.id.id_lineGraph);
//        lineGraph.setPoints(Main2Activity.dataPointsArrayList);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
    }
}
