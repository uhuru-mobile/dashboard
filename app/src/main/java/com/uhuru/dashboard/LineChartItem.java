package com.uhuru.dashboard;

/**
 * Created by Thibaut on 15/01/16.
 */
public class LineChartItem {
    private float[][] values;
    private int color;
    private String label
            ;

    public LineChartItem(float[][] values, int color, String label) {
        this.values = values;
        this.color = color;
        this.label = label;
    }

    public float[][] getValues() {
        return values;
    }

    public void setValues(float[][] values) {
        this.values = values;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}