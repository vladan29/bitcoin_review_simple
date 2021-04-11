package com.example.udimitest.utils;

import com.robinhood.spark.SparkAdapter;

public class AdapterSpark extends SparkAdapter {
    private float[] yData;

    public AdapterSpark(float[] yData) {
        this.yData = yData;
    }

    @Override
    public int getCount() {
        return yData.length;
    }

    @Override
    public Object getItem(int index) {
        return yData[index];
    }

    @Override
    public float getY(int index) {
        return yData[index];
    }
}
