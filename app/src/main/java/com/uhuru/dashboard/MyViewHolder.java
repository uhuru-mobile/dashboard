package com.uhuru.dashboard;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Thibaut on 18/01/16.
 *
 * La classe MyViewHolder permet de conserver un objet de la classe GraphicItem en mémoire lors de son affichage dans un listView
 *
 */

public class MyViewHolder {
    TextView text;
    PieChartView piechart;
    ArrayList<LineChartView> linechart;
    RelativeLayout lines_layout;
    LinearLayout line_button_layout;
    ArrayList<TextView> line_buttons;
    ListView legend;

    // viewHolder pour les séparateurs
    public MyViewHolder(TextView text) {
        this.text = text;
    }

    // viewHolder pour les PieCharts
    public MyViewHolder(PieChartView piechart, TextView text, ListView legend) {
        this.piechart = piechart;
        this.text = text;
        this.legend = legend;
    }

    public ArrayList<TextView> getLine_buttons() {
        return line_buttons;
    }

    public void setLine_buttons(ArrayList<TextView> line_buttons) {
        this.line_buttons = line_buttons;
    }

    public LinearLayout getLine_button_layout() {

        return line_button_layout;
    }

    public void setLine_button_layout(LinearLayout line_button_layout) {
        this.line_button_layout = line_button_layout;
    }

    // viewHolder pour les LineCharts
    public MyViewHolder(ArrayList<LineChartView> linechart, RelativeLayout lines_layout, LinearLayout line_button_layout, ArrayList<TextView> line_buttons) {
        this.linechart = linechart;
        this.lines_layout = lines_layout;
        this.line_button_layout = line_button_layout;
        this.line_buttons = line_buttons;

    }

    public TextView getTextView() {
        return text;
    }

    public PieChartView getPiechartView() {
        return piechart;
    }

    public void setText(TextView text) {
        this.text = text;
    }

    public void setPiechart(ArrayList<PieChartItem> list) {
        //this.text = text;

    }
    
    public void addValue(PieChartItem item){
        this.piechart.addValue(item);
    }

    public void invalidate(){
        this.piechart.invalidate();
    }

    /*public void addValue(PieChartItem item){
        this.piechart.addValue(item);
    }*/
    public ListView getLegend(){
        return this.legend;
    }
    public void setLegend(ListView legend){
        this.legend = legend;
    }

    public ArrayList<LineChartView> getLinechart() {
        return linechart;
    }

    public void setLinechart(ArrayList<LineChartView> linechart) {
        this.linechart = linechart;
    }

    public RelativeLayout getLines_layout() {
        return lines_layout;
    }

    public void setLines_layout(RelativeLayout lines_layout) {
        this.lines_layout = lines_layout;
    }

    public void addButton(TextView button){
        button.setOnClickListener(mMyClickListener);
        this.line_buttons.add(button);
    }

    private View.OnClickListener mMyClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Log.d("Dashboard", "button " + v.getText() + " clicked ID = " + v.getTag());

            int ID = 0;
            if(v.getId() == R.id.grid_button) {
                ID = 1;
                //Log.i("Dashboard", "onClick GRID on/off");
            } else {
                ID = (int)v.getTag() + 2;
            }
            //Log.d("Dashboard", "nb lineChart " + linechart.size());

            if(linechart.get(ID).getVisibility() == View.VISIBLE){
                linechart.get(ID).setVisibility(View.INVISIBLE);
                v.setBackground(v.getResources().getDrawable(R.drawable.line_chart_button));
            } else {
                linechart.get(ID).setVisibility(View.VISIBLE);
                v.setBackground(v.getResources().getDrawable(R.drawable.line_chart_button_selected));
            }


        }
    };
}
