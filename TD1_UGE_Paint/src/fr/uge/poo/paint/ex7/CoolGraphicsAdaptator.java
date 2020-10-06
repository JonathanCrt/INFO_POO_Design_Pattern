package fr.uge.poo.paint.ex7;

import com.evilcorp.coolgraphics.CoolGraphics;

public class CoolGraphicsAdaptator implements Canvas {

    private final int widthWindow;
    private final int heightWindow;
    private final CoolGraphics coolGraphics;

    public CoolGraphicsAdaptator(int widthWindow, int heightWindow, CoolGraphics coolGraphics) {
        this.widthWindow = widthWindow;
        this.heightWindow = heightWindow;
        this.coolGraphics = new CoolGraphics("Area", widthWindow, heightWindow);
    }

    private CoolGraphics.ColorPlus selectedColor(Color color){
        switch (color) {
            case BLACK:
                return CoolGraphics.ColorPlus.BLACK;
            case WHITE:
                return CoolGraphics.ColorPlus.WHITE;
            case ORANGE:
                return CoolGraphics.ColorPlus.ORANGE;
            default :
                throw new UnsupportedOperationException("This color is not valid");
        }
    }



    @Override
    public void drawLine(int x1, int y1, int x2, int y2, Color color) {
        this.coolGraphics.drawLine(x1,y1,x2,y2, this.selectedColor(color));
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, Color color) {

    }

    @Override
    public void drawEllipse(int x, int y, int width, int height, Color color) {
        this.coolGraphics.drawEllipse(x, y, width, height, selectedColor(color));
    }

    @Override
    public void clear(Color color) {
        this.coolGraphics.repaint(selectedColor(color));
    }

    @Override
    public void waitOnClick(MouseCallback mouseCallback) {
        this.coolGraphics.waitForMouseEvents((x, y) -> System.out.println("Click on (" + x + "," + y + ")"));
    }
}
