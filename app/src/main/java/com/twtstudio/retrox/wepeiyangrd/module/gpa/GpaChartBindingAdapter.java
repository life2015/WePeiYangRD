package com.twtstudio.retrox.wepeiyangrd.module.gpa;

import android.databinding.BindingAdapter;
import android.databinding.ObservableField;
import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by retrox on 2017/1/21.
 */

public class GpaChartBindingAdapter {

    @BindingAdapter({"gpaData"})
    public static void setGpaChartData(LineChart chart, final GpaBean gpaBean){

        if (gpaBean == null){
            return;
        }
        chart.setNoDataText("还没有成绩哟");
        chart.setDrawGridBackground(false);
        chart.setDragEnabled(false);
        chart.setDrawBorders(false);
        //chart.setDescription("");
        chart.setDoubleTapToZoomEnabled(false);
        chart.setPinchZoom(false);
        chart.setAutoScaleMinMaxEnabled(true);
        chart.setGridBackgroundColor(Color.BLACK);
        chart.setBorderWidth(3);
        chart.setDrawBorders(true);
        chart.getLegend().setEnabled(true);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawLabels(true);

        YAxis yAxis = chart.getAxisLeft();
        yAxis.setSpaceTop(20f);
        yAxis.setSpaceBottom(20f);
        yAxis.setDrawGridLines(true);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setEnabled(false);

        ArrayList<String> xVals = new ArrayList<>();
        ArrayList<Entry> yVals = new ArrayList<>();

        for (GpaBean.Term term : gpaBean.data) {
            int i = 0;
            xVals.add(term.name);
            yVals.add(new Entry((float) term.stat.score,i));
            i++;
        }


        LineDataSet dataSet = new LineDataSet(yVals,"label");

        LineData lineData = new LineData(dataSet);

        chart.setData(lineData);
        chart.invalidate();

//        List<Entry> entryList = new ArrayList<>();
//
//        for (GpaBean.Term term : gpaBean.data) {
//            entryList.add(term.name);
//        }
    }
}
