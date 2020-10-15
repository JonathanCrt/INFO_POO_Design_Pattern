package fr.uge.poo.paint.ex9;

public interface Canvas {

    void drawLine(int x1, int y1, int x2, int y2, EColor color);

    void drawRectangle(int x, int y, int width, int height, EColor color);

    void drawEllipse(int x, int y, int width, int height, EColor color);

    void clear(EColor color);

    void waitOnClick(MouseCallback mouseCallback);

    void render();


}

