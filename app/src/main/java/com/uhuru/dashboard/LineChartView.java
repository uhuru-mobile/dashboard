package com.uhuru.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thibaut on 15/01/16.
 */
public class LineChartView extends View {

    public static final int MODE_AXIS = 0;
    public static final int MODE_DRAW_LINES = 1;
    public static final int MODE_DRAW_GRID = 2;
    private List<LineChartItem> items;
    int mode;
    Grid grid;

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        items = new ArrayList<LineChartItem>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawChart(canvas);
        super.onDraw(canvas);
    }

    private void drawChart(Canvas canvas) {
        if (items != null) {

            // angle de départ
            float scaleX, scaleY, zeroX, zeroY;
            int angle_tour;
            double somme = getSomme();

            // Zone carrée
            RectF zoneDessin = new RectF(2, 2, getWidth()-2, getHeight() - 2);
            RectF zonetracé = new RectF(50, 50, getWidth() - 50, getWidth() - 50);
            // Pinceau
            Paint paint = new Paint();
            paint.setAntiAlias(true);


            Paint myPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            myPaint.setStrokeWidth(4/*1 /getResources().getDisplayMetrics().density*/);
            myPaint.setStyle(Paint.Style.STROKE);

            if (this.mode == MODE_AXIS) {

                // dessin des axes
                myPaint.setColor(getResources().getColor(R.color.MD_Grey_800));
                canvas.drawLine(20, getHeight() - 40, getWidth() - 20, getHeight() - 40, myPaint);
                canvas.drawLine(80, getHeight() - 20, 80, 20, myPaint);
            }
            // On commence par rechercher les extremums en X et Y pour ajuster la fenêtre à la bonne zone


            zeroX = 100;
            zeroY = 80;

            if (this.grid == null){
                Log.i("Dashboard", "drawChart GRID IS NULL");
                this.grid = new Grid();
                float[] extremum = getExtremum(items);
                this.grid.setMinimumX(extremum[0]);
                this.grid.setMaximumX(extremum[1]);
                this.grid.setMinimumY(extremum[2]);
                this.grid.setMaximumY(extremum[3]);
            } else {
                Log.i("Dashboard", "drawChart GRID IS NOT NULL");
            }



            /*Log.i("Dashboard", "drawChart min X = " + extremum[0] + " max X = " + extremum[1]);
            Log.i("Dashboard", "drawChart min Y = " + extremum[2] + " max Y = " + extremum[3]);*/


            float maxY = getHeight() - zeroY;

            scaleX = (getWidth()- 2 * zeroX)/grid.getMaximumX();

            scaleY = (getHeight()-2 * zeroY)/grid.getMaximumY();


            if (this.mode == MODE_DRAW_GRID){
                myPaint.setColor(getResources().getColor(R.color.MD_Grey_300));
                myPaint.setStrokeWidth(2);
                //canvas.drawLine(80, zeroY, getWidth() - 20, zeroY, myPaint);



                for(float i = this.grid.getMinimumY() ; i <= this.grid.getMaximumY() ; i+=1){
                    canvas.drawLine(80, maxY - (i * scaleY), getWidth() - 20, maxY - (i * scaleY), myPaint);
                }

                myPaint.setColor(getResources().getColor(R.color.MD_Grey_700));
                myPaint.setTextSize(30);
                canvas.drawText("" + Math.round(this.grid.getMinimumY()), 30, maxY - (this.grid.getMinimumY() * scaleY) + 10, myPaint);
                canvas.drawText("" + Math.round(this.grid.getMaximumY()), 30, maxY - (this.grid.getMaximumY() * scaleY) + 10, myPaint);

            }

            // On passe chaque item en revue
            for (LineChartItem item : items) {

                if (this.mode == MODE_DRAW_LINES) {
                    float[][] points = item.getValues();
                    // on change la couleur
                    myPaint.setColor(item.getColor());
                    myPaint.setStrokeWidth(8/*1 /getResources().getDisplayMetrics().density*/);
                    for (int i = 1; i < points.length; i++) {
                        canvas.drawLine(points[i - 1][0] * scaleX + zeroX, maxY - points[i - 1][1] * scaleY, points[i][0] * scaleX + zeroX, maxY - points[i][1] * scaleY, myPaint);
                    }
                }
            }
        }
    }

    private float[] getExtremum(List<LineChartItem> items){
        float minX, maxX, minY, maxY;

        if(items != null && items.size() > 0) {
            minX = items.get(0).getValues()[0][0];
            maxX = items.get(0).getValues()[0][0];

            minY = items.get(0).getValues()[0][1];
            maxY = items.get(0).getValues()[0][1];
            for (LineChartItem item : items) {
                for (int i = 0; i < item.getValues().length; i++) {
                    if (minX > item.getValues()[i][0]) {
                        minX = item.getValues()[i][0];
                    } else if (maxX < item.getValues()[i][0]) {
                        maxX = item.getValues()[i][0];
                    }
                    if (minY > item.getValues()[i][1]) {
                        minY = item.getValues()[i][1];
                    } else if (maxY < item.getValues()[i][1]) {
                        maxY = item.getValues()[i][1];
                    }
                }
            }
            return new float[]{minX, maxX, minY, maxY};
        } else
            return new float[]{0,0,0,0};
    }

    private double getSomme() {
        double somme = 0;

        /*for (LineChartItem item : items) {
            somme += item.getValues();
        }*/

        return somme;
    }

    public void addValue(LineChartItem item, int mode) {
        this.items.add(item);
        this.mode = mode;
        this.grid = null;

        if (this.grid == null){
            Log.i("Dashboard", "drawChart GRID IS NULL");
            this.grid = new Grid();
            float[] extremum = getExtremum(items);
            this.grid.setMinimumX(extremum[0]);
            this.grid.setMaximumX(extremum[1]);
            this.grid.setMinimumY(extremum[2]);
            this.grid.setMaximumY(extremum[3]);
        } else {
            Log.i("Dashboard", "drawChart GRID IS NOT NULL");
        }



    }

    public Grid getGrid(){
        return this.grid;
    }

    public void addValue(LineChartItem item, int mode, Grid grid) {
        this.items.add(item);
        this.mode = mode;
        this.grid = grid;
    }

    public void setValues(List<LineChartItem> items) {
        this.items = items;
    }

    public void refresh(){
        items = new ArrayList<LineChartItem>();
    }

}
