package fr.uge.poo.paint.ex4;

import java.awt.*;

public interface Shape {
    void draw(Graphics2D graphics2D);

    double getDistanceBetweenCenterAndClick(int x, int y);
}
