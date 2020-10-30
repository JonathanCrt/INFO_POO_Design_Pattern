package fr.uge.poo.paint.ex9;

public class Square implements Shape {

    private final Rectangle square;

    public Square(int x, int y, int sizeSide) {
        this.square = new Rectangle(x, y, sizeSide, sizeSide);
    }

    @Override
    public void draw(Canvas canvas, EColor color) {
        this.square.draw(canvas, color);
    }

    @Override
    public double computeDistanceBetweenCenterAndUserClick(int x, int y) {
        return this.square.computeDistanceBetweenCenterAndUserClick(x, y);
    }

    @Override
    public String toString() {
        return "square" +
                " " + square.x +
                " " + square.y +
                " " + square.width +
                " " + square.height;
    }

}
