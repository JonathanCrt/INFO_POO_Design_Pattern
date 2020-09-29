package fr.uge.poo.paint.ex4;

import fr.uge.poo.paint.ex3.Shape;
import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;

public abstract class AbstractShape implements Shape {

    private final int x0;
    private final int x1;
    private final int y0;
    private final int y1;
    private final int width;
    private final int height;

    public AbstractShape(int x0, int x1, int y0, int y1, int width, int height) {
        this.x0 = x0;
        this.x1 = x1;
        this.y0 = y0;
        this.y1 = y1;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(Graphics2D graphic, String typeGraphic) {
        graphic.setColor(Color.BLACK);
        switch (typeGraphic) {
            case "line":
                graphic.drawLine(x0, y0, x1, y1);
                break;
            case "rectangle":
                graphic.drawRect(x0, y0, width, height);
                break;
            case "ellipse":
                graphic.drawOval(x0, y0, width, height);
        }
    }
}
