package fr.uge.poo.paint.ex6;

import java.awt.*;

/**
 * Package visibility for class
 * private fields ? no , otherwise we need getters !
 * protected : no! user into another package can extends from our class
 * windows size  -> during file parsing ? bad idea
 */
abstract class AbstractShape implements Shape {

    int x;
    int y;
    int width;
    int height;

    public AbstractShape(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double computeDistanceBetweenCenterAndUserClick(int x, int y) {
        var middleX = (x + width) / 2;
        var middleY = (y + height) / 2;

        return Point.distance(middleX, middleY, x, y);
    }

    @Override
    public int minWidth() {
        return width;
    }

    @Override
    public int minHeight() {
        return height;
    }


}
