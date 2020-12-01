package fr.uge.poo.paint.ex7;

public interface Shape {
    void draw(Canvas canvas, EColor color);

    double computeDistanceBetweenCenterAndUserClick(int x, int y);

    //double minWidth();

    //double minHeight();
}
