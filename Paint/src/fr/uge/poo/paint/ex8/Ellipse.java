package fr.uge.poo.paint.ex8;

public class Ellipse extends AbstractShape {

    public Ellipse(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Canvas canvas, EColor color) {
        canvas.drawEllipse(x, y, width, height, color);
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
