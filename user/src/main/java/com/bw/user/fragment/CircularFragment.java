package com.bw.user.fragment;


import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;

import com.bw.framwork.base.BaseFragment;
import com.bw.user.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;


public class CircularFragment extends BaseFragment {
    private LineChart wendu;
    private HorizontalScrollView scrollView;

    @Override
    protected int bandlayout() {
        return R.layout.cirrfragment;
    }

    @Override
    protected void initview() {
        wendu = (LineChart) findViewById(R.id.wendu);
        scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        scrollView.scrollTo(1000,0);
    }

    @Override
    protected void initdata() {
        LineData mLineData  = getLineData(7, 40);
        showChart(wendu, mLineData , Color.rgb(114, 188, 223));
    }

    /**
     * 生成一个数据
     *
     * @param count 表示图表中有多少个坐标点
     * @param range 用来生成range以内的随机数
     * @return
     */
    private LineData getLineData(int count, float range) {
        ArrayList<String> xValues = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            // x轴显示的数据，这里默认使用数字下标显示
            xValues.add("" + i);
        }

        ArrayList<LineDataSet> lineDataSets = new ArrayList<LineDataSet>();
        // y轴的数据
        ArrayList<Entry> yValues = new ArrayList<Entry>();
        for (int i = 0; i < count; i++) {
            float value = (float) (Math.random() * range) + 3;
            yValues.add(new Entry(value, i));
        }
        // y轴的数据集合
        LineDataSet lineDataSet = new LineDataSet(yValues, "" /*测试折线图*/);
        //用y轴的集合来设置参数
        // 线宽
        lineDataSet.setLineWidth(1.0f);
        // 显示的圆形大小
        lineDataSet.setCircleSize(2.5f);
        // 显示颜色
        lineDataSet.setColor(Color.GREEN);
        // 圆形的颜色
        lineDataSet.setCircleColor(Color.GREEN);
        // 高亮的线的颜色
        lineDataSet.setHighLightColor(Color.GREEN);
        // 设置圆点的颜色
        lineDataSet.setFillColor(Color.GREEN);
        lineDataSet.setDrawCircleHole(false);
        //lineDataSet.setValueTextSize(9f);
        lineDataSet.setFillAlpha(65);

        lineDataSets.add(lineDataSet);
        LineData lineData = new LineData(xValues, lineDataSet);
        return lineData;
    }

    /**
     * 显示数据
     *
     * @param lineChart
     * @param lineData
     * @param color
     */
    private void showChart(LineChart lineChart, LineData lineData, int color) {
        //是否在折线图上添加边框
        lineChart.setDrawBorders(false);  //是否在折线图上添加边框

        // 数据描述
        lineChart.setDescription("2");
        // 如果没有数据的时候，会显示这个，类似listview的emtpyview
        lineChart.setNoDataTextDescription("暂无数据");

        // 是否显示表格颜色
        lineChart.setDrawGridBackground(false);
        // 表格的的颜色，在这里是是给颜色设置一个透明度
        //lineChart.setGridBackgroundColor(Color.WHITE & 0x70FFFFFF);

        // 设置是否可以触摸
        lineChart.setTouchEnabled(true);
        // 是否可以拖拽
        lineChart.setDragEnabled(true);
        // 是否可以缩放
        lineChart.setScaleEnabled(true);

        // if disabled, scaling can be done on x- and y-axis separately
        lineChart.setPinchZoom(false);

        //lineChart.setBackgroundColor(Color.rgb(Integer.parseInt("7e", 16), Integer.parseInt("ce", 16), Integer.parseInt("f4", 16)));// 设置背景
        // 设置数据
        lineChart.setData(lineData);

        // 设置比例图标示，就是那个一组y的value的
        Legend mLegend = lineChart.getLegend();
        //mLegend.setPosition(LegendPosition.BELOW_CHART_CENTER);
        // 样式
        mLegend.setForm(Legend.LegendForm.CIRCLE);
        // 字体
        mLegend.setFormSize(0f);
        // 颜色
        mLegend.setTextColor(Color.RED);
        // 字体
        //mLegend.setTypeface(mTf);

        // 设置Y轴右边不显示数字
        lineChart.getAxisRight().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        // 设置X轴的数据显示在报表的下方
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //xAxis.setDrawAxisLine(false);
        // 设置不从X轴发出纵向直线
        xAxis.setDrawGridLines(false);
        // 立即执行的动画,x轴
        lineChart.animateX(2500);
    }

}
