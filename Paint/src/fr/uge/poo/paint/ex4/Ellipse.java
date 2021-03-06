package fr.uge.poo.paint.ex4;

import java.awt.*;
import java.util.Objects;

public class Ellipse extends AbstractShape {

    public Ellipse(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        Objects.requireNonNull(graphics2D);
        graphics2D.setColor(Color.BLACK);
        graphics2D.drawOval(x, y, width, height);
    }

    @Override
    public String toString() {
        return "Ellipse{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
