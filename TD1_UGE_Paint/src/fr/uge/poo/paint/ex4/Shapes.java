package fr.uge.poo.paint.ex4;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.util.*;
import java.util.List;

/*
    On encapsule la listes des figures dans cette classes pour déléguer
 */
public class Shapes {
    private List<Shape> shapeList;

    public Shapes() {
        this.shapeList = new ArrayList<>();
    }

    public void add(Shape shape) {
        Objects.requireNonNull(shape);
        this.shapeList.add(shape);
    }

    public void loopAndDraw(SimpleGraphics graphics) {
        for (Shape shape : shapeList) {
            graphics.render(shape::draw);
        }
    }

    public Optional<Shape> findMinShapeFromDistance(int x, int y) {
        return shapeList
                .stream()
                .min(Comparator.comparingDouble(shape -> shape.getDistanceBetweenCenterAndClick(x, y)));
    }

}
