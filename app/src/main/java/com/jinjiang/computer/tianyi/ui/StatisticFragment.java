package com.jinjiang.computer.tianyi.ui;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.jinjiang.computer.tianyi.R;
import com.jinjiang.computer.tianyi.entity.Student;
import com.jinjiang.computer.tianyi.output.AbstractOutput;
import com.jinjiang.computer.tianyi.output.statisticShow;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SuppressLint("ValidFragment")
public class StatisticFragment extends Fragment{

    private PieChart pc;
    private int heres=0;
    private int sicks=0;
    private int lates=0;
    private int absents=0;
    private statisticShow ss;
    private Field field;
    private List<Student> ls;
    private TextView totalcount;

    public StatisticFragment()
    {

    }

    public StatisticFragment(String classname)
    {
        ss = new statisticShow();
        try {
            field = Student.class.getDeclaredField("absent");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        ls = ss.orderBy(classname, field, false);//降序 并获得list

        for(int i=0;i<ls.size();i++)
        {
            heres+=ls.get(i).getTotal();
            lates+=ls.get(i).getLate();
            sicks+=ls.get(i).getIll();
            absents+=ls.get(i).getAbsent();
        }

        heres=heres-lates-sicks-absents;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        pc = (PieChart)view.findViewById(R.id.piechart);
        PieData mPieData = getPieData();
        showChart(pc, mPieData);
        totalcount = (TextView)view.findViewById(R.id.totalcount);
        if(ls!=null)
        if(ls.size()>=1)
        totalcount.setText("共点到："+ls.get(0).getTotal()+"次 共请假："+sicks+"人 共迟到："+lates+"人 共缺勤："+absents+"人");
        return view;
    }

    private PieData getPieData() {

        ArrayList<String> xValues = new ArrayList<String>();  //xVals用来表示每个饼块上的内容

        xValues.add("已到");
        xValues.add("请假");
        xValues.add("迟到");
        xValues.add("缺勤");  //饼块上显示成Quarterly1, Quarterly2, Quarterly3, Quarterly4


        ArrayList<Entry> yValues = new ArrayList<Entry>();  //yVals用来表示封装每个饼块的实际数据

        // 饼图数据
        /**
         * 将一个饼形图分成四部分， 四部分的数值比例为14:14:34:38
         * 所以 14代表的百分比就是14%
         */
        float quarterly1 = heres;
        float quarterly2 = sicks;
        float quarterly3 = lates;
        float quarterly4 = absents;

        yValues.add(new Entry(quarterly1, 0));
        yValues.add(new Entry(quarterly2, 1));
        yValues.add(new Entry(quarterly3, 2));
        yValues.add(new Entry(quarterly4, 3));

        //y轴的集合
        PieDataSet pieDataSet = new PieDataSet(yValues, "点到当前情况"/*显示在比例图上*/);
        pieDataSet.setSliceSpace(0f); //设置个饼状图之间的距离

        ArrayList<Integer> colors = new ArrayList<Integer>();

        // 饼图颜色
        colors.add(Color.rgb(57, 135, 200));//已到
        colors.add(Color.rgb(114, 188, 223));//请假
        colors.add(Color.rgb(255, 123, 124));//迟到
        colors.add(Color.rgb(205, 205, 205));//缺勤
        pieDataSet.setColors(colors);

        DisplayMetrics metrics = getResources().getDisplayMetrics();
        float px = 5 * (metrics.densityDpi / 160f);
        pieDataSet.setSelectionShift(px); // 选中态多出的长度

        PieData pieData = new PieData(xValues, pieDataSet);

        return pieData;
    }

    private void showChart(PieChart pieChart, PieData pieData) {
        pieChart.setHoleColorTransparent(true);

        pieChart.setHoleRadius(60f);  //半径
        pieChart.setTransparentCircleRadius(64f); // 半透明圈
        //pieChart.setHoleRadius(0)  //实心圆

        pieChart.setDescription("统计");

        // mChart.setDrawYValues(true);
        pieChart.setDrawCenterText(true);  //饼状图中间可以添加文字

        pieChart.setDrawHoleEnabled(true);

        pieChart.setRotationAngle(90); // 初始旋转角度

        // draws the corresponding description value into the slice
        // mChart.setDrawXValues(true);

        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true); // 可以手动旋转

        // display percentage values
        pieChart.setUsePercentValues(true);  //显示成百分比
        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
//      mChart.setOnChartValueSelectedListener(this);
        // mChart.setTouchEnabled(false);

//      mChart.setOnAnimationListener(this);


        pieChart.setCenterText("点到情况");  //饼状图中间的文字

        //设置数据
        pieChart.setData(pieData);

        // undo all highlights
//      pieChart.highlightValues(null);
//      pieChart.invalidate();

        Legend mLegend = pieChart.getLegend();  //设置比例图
        mLegend.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);  //最右边显示
//      mLegend.setForm(LegendForm.LINE);  //设置比例图的形状，默认是方形
        mLegend.setXEntrySpace(7f);
        mLegend.setYEntrySpace(5f);

        pieChart.animateXY(1000, 1000);  //设置动画
        // mChart.spin(2000, 0, 360);
    }



}
