package fr.uge.poo.paint.ex3;

import java.awt.*;

public class Ellipse implements Shape {

    int x;
    int y;
    int width;
    int length;

    public Ellipse(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval(x, y, width, length);
    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
