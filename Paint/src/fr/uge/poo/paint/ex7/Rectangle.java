package fr.uge.poo.paint.ex7;

public class Rectangle extends AbstractShape {

    public Rectangle(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void draw(Canvas canvas, EColor color) {
        canvas.drawRectangle(x, y, width, height, color);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
