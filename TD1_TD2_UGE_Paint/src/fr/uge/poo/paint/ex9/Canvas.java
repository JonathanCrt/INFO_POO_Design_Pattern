package fr.uge.poo.paint.ex9;

import com.evilcorp.coolgraphics.CoolGraphics;
import fr.uge.poo.simplegraphics.SimpleGraphics;

public interface Canvas {

    void drawLine(int x1, int y1, int x2, int y2, EColor color);

    void drawRectangle(int x, int y, int width, int height, EColor color);

    void drawEllipse(int x, int y, int width, int height, EColor color);

    void clear(EColor color);

    void waitOnClick(MouseCallback mouseCallback);

    void paint();

    default  SimpleGraphics simpleGraphicsFactory(String nameArea, int windowsWidth, int windowsHeight) {
        return new SimpleGraphics(nameArea, windowsWidth, windowsHeight);
    }

     static CoolGraphicsAdapter coolGraphicsFactory(int windowsWidth, int windowsHeight) {
        return new CoolGraphicsAdapter(windowsWidth, windowsHeight);
     }
}

