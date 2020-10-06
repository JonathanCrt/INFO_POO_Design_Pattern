package fr.uge.poo.paint.ex7;

public interface Canvas {

    void drawLine(int x1, int y1, int x2, int y2, Color color);

    void drawRectangle(int x, int y, int width, int height, Color color);

    void drawEllipse(int x, int y, int width, int height, Color color);

    void clear(Color color);

    void waitOnClick(MouseCallback mouseCallback);

    enum Color {
        BLACK, WHITE, ORANGE
    }

}
