package com.uhuru.dashboard;

/**
 * Created by Thibaut on 15/01/16.
 *
 * La classe cellule permet de représenter un événement de sécurité de manière visuelle et lisible par un utilisateur
 *
 */
public class cellule {
    private int icon;
    private String msg;
    private String date;
    private String comment;

    public cellule(int icon, String msg, String date) {
        this.icon = icon;
        this.msg = msg;
        this.date = date;
        this.comment = null;
    }

    public cellule(int icon, String msg, String date, String comment) {
        this.icon = icon;
        this.msg = msg;
        this.date = date;
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
