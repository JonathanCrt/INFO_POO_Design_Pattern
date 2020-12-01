package fr.uge.poo.paint.ex8;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;

public class SimpleGraphicsAdapter implements Canvas {

    private final SimpleGraphics simpleGraphics;


    public SimpleGraphicsAdapter(int widthWindow, int heightWindow) {
        this.simpleGraphics = new SimpleGraphics("Area with SimpleGraphics", widthWindow, heightWindow);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, EColor color) {
        this.simpleGraphics.render(graphics2D -> {
            graphics2D.setColor(selectedColor(color));
            graphics2D.drawLine(x1, y1, x2, y2);
        });
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, EColor color) {
        this.simpleGraphics.render(graphics2D -> {
            graphics2D.setColor(selectedColor(color));
            graphics2D.drawRect(x, y, width, height);
        });
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height, EColor color) {
        this.simpleGraphics.render(graphics2D -> {
            graphics2D.setColor(selectedColor(color));
            graphics2D.drawOval(x, y, width, height);
        });
    }


    private Color selectedColor(EColor color) {
        return switch (color) {
            case WHITE -> Color.WHITE;
            case ORANGE -> Color.ORANGE;
            default -> Color.BLACK;
        };
    }

    @Override
    public void clear(EColor color) {
        this.simpleGraphics.clear(selectedColor(color));
    }

    @Override
    public void waitOnClick(MouseCallback mouseCallback) {
        this.simpleGraphics.waitForMouseEvents(mouseCallback::waitOnClick);
    }
}
