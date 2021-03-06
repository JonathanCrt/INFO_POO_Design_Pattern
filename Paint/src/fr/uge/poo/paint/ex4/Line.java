package fr.uge.poo.paint.ex4;

import java.awt.*;
import java.util.Objects;

public class Line implements Shape {

    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;

    public Line(int x1, int x2, int y1, int y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public void draw(Graphics2D graphic2D) {
        Objects.requireNonNull(graphic2D);
        graphic2D.setColor(Color.BLACK);
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
