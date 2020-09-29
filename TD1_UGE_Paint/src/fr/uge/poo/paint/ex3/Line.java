package fr.uge.poo.paint.ex3;

import java.awt.*;

public class Line {

    int x0;
    int x1;
    int y0;
    int y1;

    public Line(int x0, int x1, int y0, int y1) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
    }

    public void draw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.drawLine(x0, x1, y0, y1);
    }

    @Override
    public String toString() {
        return "Line{" +
                "x0=" + x0 +
                ", x1=" + x1 +
                ", y0=" + y0 +
                ", y1=" + y1 +
                '}';
    }
}
