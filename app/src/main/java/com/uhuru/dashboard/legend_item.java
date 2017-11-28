package com.uhuru.dashboard;

/**
 * Created by Thibaut on 15/01/16.
 */
public class legend_item {
    private int color;
    private String title;
    private int nb;

    public legend_item(int color, String title, int nb) {
        this.color = color;
        this.title = title;
        this.nb = nb;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNb() {
        return nb;
    }

    public void setNb(int nb) {
        this.nb = nb;
    }
}
