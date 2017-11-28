package com.uhuru.dashboard;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
public class PieChartView extends View {

    private List<PieChartItem> items;

    public PieChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        items = new ArrayList<PieChartItem>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawChart(canvas);
        super.onDraw(canvas);
    }

    private void drawChart(Canvas canvas) {
        if (items != null) {

            // angle de départ
            float angleDepart = 180.f;
            int angle_tour;
            double somme = getSomme();

            // Zone carrée
            RectF zoneDessin = new RectF(2, 2, getWidth()-2, getWidth()-2);
            RectF zoneInterieure = new RectF(88, 88, getWidth() - 88, getWidth() - 88);
            // Pinceau
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            /*paint.setStyle(Paint.Style.FILL);
    paint.setColor(Color.MAGENTA);
    canvas.drawRect(r, paint);

    // border
    paint.setStyle(Paint.Style.STROKE);
    paint.setColor(Color.BLACK);
    canvas.drawRect(r, paint);

             */
            // remise à zéro de l'espace de dessin
            /*paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle(getWidth() / 2, getWidth() / 2, getWidth() / 2, paint);*/
            // On passe chaque item en revue
            angle_tour = 360 - 2 * items.size();
            for (PieChartItem item : items) {

                //On passe en mode remplissage
                paint.setStyle(Paint.Style.FILL);

                // on change la couleur
                paint.setColor(item.getColor());

                // On calcul l'angle d'ouverutre
                float sweepAngle = (float) ((item.getValue() / somme) * angle_tour);

                // On dessine l'arc plein
                canvas.drawArc(zoneDessin, angleDepart, sweepAngle, true, paint);

                //Puis la bordure extérieure
                paint.setStyle(Paint.Style.STROKE); //(mode bordure)
                paint.setStrokeWidth(4); // épaisseur
                paint.setColor(item.getStrokeColor());
                canvas.drawArc(zoneDessin, angleDepart, sweepAngle, true, paint);
                canvas.drawArc(zoneInterieure, angleDepart, sweepAngle, true, paint);
                // Puis la bordure intérieure


                // On change l'angle de départ
                angleDepart += sweepAngle + 2;
            }

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(getResources().getColor(R.color.white));
            canvas.drawCircle(getWidth()/2, getWidth()/2, getWidth()/2 - 90, paint);
        }
    }

    private double getSomme() {
        double somme = 0;

        for (PieChartItem item : items) {
            somme += item.getValue();
        }

        return somme;
    }

    public void addValue(PieChartItem item) {
        this.items.add(item);
    }

    public void setValues(List<PieChartItem> items) {
        this.items = items;
    }
    public void resetPieChart(){
        //angleD
    }

    public void refresh(){
        items = new ArrayList<PieChartItem>();
    }

}
