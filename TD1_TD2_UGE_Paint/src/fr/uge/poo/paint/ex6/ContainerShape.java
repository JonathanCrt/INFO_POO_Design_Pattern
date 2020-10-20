package fr.uge.poo.paint.ex6;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/*
    On encapsule la listes des figures dans cette classes pour déléguer
 */
public class ContainerShape {
    private final List<Shape> shapeList;

    private Shape selectedShape;

    private ContainerShape() {
        this.shapeList = new ArrayList<>();
    }

    public static ContainerShape from(Path path) throws IOException {
        var shapesList = new ContainerShape();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);

                switch (tokens[0]) {
                    case "line" -> shapesList.add(new Line(x1, x2, y1, y2));
                    case "ellipse" -> shapesList.add(new Ellipse(x1, y1, x2, y2));
                    case "rectangle" -> shapesList.add(new Rectangle(x1, y1, x2, y2));
                    default -> throw new UnsupportedOperationException("This shape doesn't exist");
                }
            });
        }
        return shapesList;
    }

    public void add(Shape shape) {
        Objects.requireNonNull(shape);
        this.shapeList.add(shape);
    }

    public WindowsSize computeWindowsSize(){
        var width = shapeList.stream().map(Shape::minWidth).max(Comparator.naturalOrder()).orElse(500);
        var height = shapeList.stream().map(Shape::minHeight).max(Comparator.naturalOrder()).orElse(500);
        return new WindowsSize(width, height);

        /*
        return shapeList.stream()
                .map(shape -> new WindowsSize(shape.minWidth(), shape.minHeight()))
                .reduce(new WindowsSize(0, 0), WindowsSize::)
        */

    }

    public void loopAndDraw(SimpleGraphics graphics) {
        Objects.requireNonNull(graphics);
        shapeList.forEach(shape -> graphics.render(graphics2D -> shape.draw(graphics2D, Color.BLACK)));
    }

    private Optional<Shape> findMinShapeFromDistance(int x, int y) {
        return shapeList
                .stream()
                .min(Comparator.comparingDouble(shape -> shape.computeDistanceBetweenCenterAndUserClick(x, y)));
    }

    public void changeColor(int x, int y, Color color, SimpleGraphics area) {
        Objects.requireNonNull(color);
        Objects.requireNonNull(area);
        var optionalShape = findMinShapeFromDistance(x, y);
        optionalShape.ifPresent(shape -> {
            this.selectedShape = shape;
            area.render(graphics2D -> shape.draw(graphics2D, color));
        });
    }
}
