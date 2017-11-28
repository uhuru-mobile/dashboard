package com.uhuru.dashboard;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Thibaut on 18/01/16.
 *
 * La classe GraphicItem permet de représenter des données en fonction des types suivants :
 *  - TYPE_PIE : un affichage des données en anneau avec légende sur le côté (layout/item_pie.xml)
 *  - TYPE_LINE : un affichage des données en absisse/ordonnée avec légende clickable
 *  - TYPE_SELECTOR : le titre d'un graphique
 *
 */

public class GraphicItem {
    // type = TYPE_SELECTOR, TYPE_PIE ou TYPE_LINE
    private int type;
    // paramètres pour TYPE_SEPARATOR
    private String text;

    // paramètres pour TYPE_PIE
    private ArrayList<PieChartItem> pie_items; // la liste des parts du camembert
    private int total_events; // le nombre total d'événement représentés par le camembert

    // paramètres pour TYPE_LINE
    private ArrayList<LineChartItem> line_items; // la liste des graphes à afficher

    // Constructeur pour le séparateur
    public GraphicItem(String text, int type) {
        this.text = text;
        this.type = type;
    }

    // Constructeur pour le PieChart
    public GraphicItem(ArrayList<PieChartItem> items, int total_events, int type){
        this.pie_items = items;
        this.total_events = total_events;
        this.type = type;
    }

    //Constructeur pour le LineChart
    public GraphicItem(ArrayList<LineChartItem> items, int type){
        this.line_items = items;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setPie(ArrayList<PieChartItem> items){
        this.pie_items = items;
    }

    public ArrayList<PieChartItem> getPie(){
        return pie_items;
    }

    public void setTotal_events(int nb){
        this.total_events = nb;
    }

    public int getTotal_events(){
        return this.total_events;
    }

    public void clean_pie(){
        this.pie_items = null;
    }

    public ArrayList<LineChartItem> getLine_items() {
        return line_items;
    }

    public void setLine_items(ArrayList<LineChartItem> line_items) {
        this.line_items = line_items;
    }
}
