package fr.uge.poo.paint.ex4;

import java.awt.*;

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

}
