package fr.uge.poo.paint.ex5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.util.List;
import java.util.*;

/*
    On encapsule la listes des figures dans cette classes pour déléguer
 */
public class ContainerShape {
    private List<Shape> shapeList;

    public ContainerShape() {
        this.shapeList = new ArrayList<>();
    }

    public void add(Shape shape) {
        Objects.requireNonNull(shape);
        this.shapeList.add(shape);
    }

    public void loopAndDraw(SimpleGraphics graphics) {
        for (Shape shape : shapeList) {
            graphics.render(graphics2D -> shape.draw(graphics2D, Color.BLACK));
        }
    }

    private Optional<Shape> findMinShapeFromDistance(int x, int y) {
        return shapeList
                .stream()
                .min(Comparator.comparingDouble(shape -> shape.computeDistanceBetweenCenterAndUserClick(x, y)));
    }

    public void changeColor(int x, int y, Color color, SimpleGraphics area) {
        var optionalShape = findMinShapeFromDistance(x, y);
        optionalShape.ifPresent(shape -> area.render(graphics2D -> shape.draw(graphics2D, color)));
    }
}
