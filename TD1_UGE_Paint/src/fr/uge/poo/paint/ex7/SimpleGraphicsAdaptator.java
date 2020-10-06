package fr.uge.poo.paint.ex7;

import fr.uge.poo.simplegraphics.SimpleGraphics;

public class SimpleGraphicsAdaptator implements Canvas{

    private final int widthWindow;
    private final int heightWindow;
    private final SimpleGraphics simpleGraphics;


    public SimpleGraphicsAdaptator(int widthWindow, int heightWindow) {
        this.widthWindow = widthWindow;
        this.heightWindow = heightWindow;
        this.simpleGraphics = new SimpleGraphics("Area", this.widthWindow, this.heightWindow);
    }

    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        this.simpleGraphics.render(graphics2D -> graphics2D.drawLine(x1, y1, widthWindow, heightWindow));
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color) {
        this.simpleGraphics.render(graphics2D -> graphics2D.drawRect(x, y, width, height));
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height, Color color) {
        this.simpleGraphics.render(graphics2D -> graphics2D.drawOval(x, y, width, height));
    }

    @Override
    public void clear(Color color) {
        this.simpleGraphics.render(graphics2D -> {
            switch (color) {
                case BLACK:
                    graphics2D.setColor(java.awt.Color.BLACK);
                    break;
                case WHITE:
                    graphics2D.setColor(java.awt.Color.WHITE);
                    break;
                case ORANGE:
                    graphics2D.setColor(java.awt.Color.ORANGE);
                    break;
                default:
                    throw new UnsupportedOperationException("This color is not valid");
            }
        });
    }

    @Override
    public void waitOnClick(MouseCallback mouseCallback) {

    }
}
