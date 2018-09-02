package com.example.chandan.bmicalculatordemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Scanner;

public class graphAct extends AppCompatActivity {
    GraphView graph;
    TextView tvGraph;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        graph = (GraphView) findViewById(R.id.graph);
        tvGraph = (TextView) findViewById(R.id.tvGraph);
        int cnt = Activity2.db.viewBmiGraphCount();
        String info ="On X-Axis -> Record number \nOn Y-Axis -> BMI ";


        System.out.println(cnt);
        String data1 = Activity2.db.viewBmiGraph();

        if (data1.length() == 0)
            tvGraph.setText("No records to show");
        else {


            Scanner scan = new Scanner(data1);

            scan.useDelimiter(" ");


            String n2 = "";
            String n3 = "";
            double d = 0.0;
            int j;
            ArrayList<Double> list = new ArrayList<>();
            for (j = 0; j < cnt; j++) {
                n2 = scan.next();
                System.out.println(n2);
                d = Double.parseDouble(n2);
                /*Double d1 = new Double(d);
                int k = d1.intValue();*/
                list.add(d);

            }

            StringBuffer sb1 = new StringBuffer("");
            /*for(Integer m:list) {
                sb1.append(m + " ");
            }*/
            String temp = Integer.toString(cnt);
            tvGraph.setText(info);
            double t = 0.0;
            int k = 0;
            String f[];

            /*f = new String[cnt];
            for (k = 0; k < cnt; k++) {
                f[k] = scandates.next();}
            StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
            staticLabelsFormatter.setHorizontalLabels(f);
            staticLabelsFormatter.setVerticalLabels(null);*/


            DataPoint[] points = new DataPoint[cnt];
            for (int i = 0; i < points.length; i++) {
                t = list.get(i);

               /* LocalDate al=LocalDate.parse(dateGraph,dt);
                date = Date.from(al.atStartOfDay(ZoneId.systemDefault()).toInstant());
               // date=calendar.getTime();*/

                points[i] = new DataPoint(i+1,t);



            }



                LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);

        series.setAnimated(true);
               // set manual X bounds
               graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(0);
                graph.getViewport().setMaxY(40);

            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(5);

                // enable scaling and scrolling
          graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.getViewport().setScrollable(true);
            graph.addSeries(series);


// set manual x bounds to have nice steps


// as we use dates as labels, the human rounding to nice readable numbers
// is not necessary






            }


        }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(graphAct.this, Activity2.class);
        startActivity(i);
        finish();
    }

    }
