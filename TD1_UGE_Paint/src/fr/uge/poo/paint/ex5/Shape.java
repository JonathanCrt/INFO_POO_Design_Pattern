package fr.uge.poo.paint.ex5;

import java.awt.*;

public interface Shape {
    void draw(Graphics2D graphics2D, Color color);
    double computeDistanceBetweenCenterAndUserClick(int x, int y);
}
