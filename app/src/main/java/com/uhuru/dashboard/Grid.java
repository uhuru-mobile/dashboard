package com.uhuru.dashboard;

/**
 * Created by Thibaut on 20/01/16.
 *
 * La classe Grid permet de sauvegarder la fenêtre d'affichage d'un GraphicItem de type TYPE_LINE
 *
 */
public class Grid {
    private float minimumX, maximumX, minimumY, maximumY;

    public Grid() {
    }

    public float getMinimumX() {
        return minimumX;
    }

    public void setMinimumX(float minimumX) {
        this.minimumX = minimumX;
    }

    public float getMaximumX() {
        return maximumX;
    }

    public void setMaximumX(float maximumX) {
        this.maximumX = maximumX;
    }

    public float getMinimumY() {
        return minimumY;
    }

    public void setMinimumY(float minimumY) {
        this.minimumY = minimumY;
    }

    public float getMaximumY() {
        return maximumY;
    }

    public void setMaximumY(float maximumY) {
        this.maximumY = maximumY;
    }
}
