package fr.uge.poo.paint.ex6;

import java.awt.*;

public class Line implements Shape {

    int x1;
    int x2;
    int y1;
    int y2;

    public Line(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D graphic2D, Color color) {
        graphic2D.setColor(color);
        graphic2D.drawLine(x1, y1, x2, y2);
    }

    @Override
    public double computeDistanceBetweenCenterAndUserClick(int x, int y) {

        var middleX = (x1 + x2) / 2;
        var middleY = (y1 + y2) / 2;
        return Point.distance(middleX, middleY, x, y);
    }

    @Override
    public String toString() {
        return "Line{" +
                "x1=" + x1 +
                ", x2=" + x2 +
                ", y1=" + y1 +
                ", y2=" + y2 +
                '}';
    }
}
