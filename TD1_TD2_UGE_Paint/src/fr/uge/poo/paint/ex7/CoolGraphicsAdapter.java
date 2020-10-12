package fr.uge.poo.paint.ex7;

import com.evilcorp.coolgraphics.CoolGraphics;

public class CoolGraphicsAdapter implements Canvas {

    private final int widthWindow;
    private final int heightWindow;
    private final CoolGraphics coolGraphics;

    public CoolGraphicsAdapter(int widthWindow, int heightWindow) {
        this.widthWindow = widthWindow;
        this.heightWindow = heightWindow;
        this.coolGraphics = new CoolGraphics("Area", widthWindow, heightWindow);
    }

    private CoolGraphics.ColorPlus selectedColor(EColor color) {
        return switch (color) {
            case BLACK -> CoolGraphics.ColorPlus.BLACK;
            case WHITE -> CoolGraphics.ColorPlus.WHITE;
            case ORANGE -> CoolGraphics.ColorPlus.ORANGE;
        };
    }


    @Override
    public void drawLine(int x1, int y1, int x2, int y2, EColor color) {
        this.coolGraphics.drawLine(x1, y1, x2, y2, this.selectedColor(color));
    }

    @Override
    public void drawRectangle(int x, int y, int width, int height, EColor color) {
        this.coolGraphics.drawLine(x, y, x + width, y, this.selectedColor(color));
        this.coolGraphics.drawLine(x, y, x, y + height, this.selectedColor(color));
        this.coolGraphics.drawLine(x + width, y, x + width, y + height, this.selectedColor(color));
        this.coolGraphics.drawLine(x, y + height, x + width, y + height, this.selectedColor(color));
    }

    @Override
    public void drawEllipse(int x, int y, int width, int height, EColor color) {
        this.coolGraphics.drawEllipse(x, y, width, height, selectedColor(color));
    }

    @Override
    public void clear(EColor color) {
        this.coolGraphics.repaint(selectedColor(color));
    }

    @Override
    public void waitOnClick(MouseCallback mouseCallback) {
        this.coolGraphics.waitForMouseEvents(mouseCallback::waitOnClick);
    }
}
