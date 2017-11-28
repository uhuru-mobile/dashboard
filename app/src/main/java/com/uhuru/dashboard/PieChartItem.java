package com.uhuru.dashboard;

/**
 * Created by Thibaut on 15/01/16.
 */
public class PieChartItem {
    private double value;
    private int color;
    private int stroke_color;
    private String legend;

    public PieChartItem(double value, int color, int stroke_color, String legend) {
        this.value = value;
        this.color = color;
        this.stroke_color = stroke_color;
        this.legend = legend;
    }
    public double getValue() {
        return value;
    }
    public void setValue(double value) {
        this.value = value;
    }
    public int getColor() {
        return color;
    }
    public void setColor(int color) {
        this.color = color;
    }
    public int getStrokeColor() {
        return stroke_color;
    }
    public void setStrokeColor(int color) {
        this.stroke_color = color;
    }
    public String getLegend(){
        return this.legend;
    }
    public void setLegend(String legend){
        this.legend = legend;
    }
}