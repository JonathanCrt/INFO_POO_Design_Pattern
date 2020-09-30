package fr.uge.poo.paint.ex3;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Paint {

    private List<Shape> shapes = new ArrayList<>();

    public void parseFile(Path path) throws IOException {
        try (Stream lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.toString().split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);

                switch (tokens[0]) {
                    case "line":
                        shapes.add(new Line(x1, x2, y1, y2));
                        break;
                    case "ellipse":
                        shapes.add(new Ellipse(x1, y1, x2, y2));
                        break;
                    case "rectangle":
                        shapes.add(new Rectangle(x1, y1, x2, y2));
                        break;
                    default:
                        System.out.println("This shape doesn't exist");
                }

            });

        }
    }

    public void loopAndDraw(SimpleGraphics graphics) {
        for (Shape shape : shapes) {
            graphics.render(shape::draw);
        }
    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:/Users/jonat/Documents/GitHub/INFO_POO_Design_Pattern/TD1_UGE_Paint/src/fr/uge/poo/paint/file/draw2.txt");
        var simpleGraphics = new SimpleGraphics("figure", 800, 600);
        simpleGraphics.clear(Color.white);

        var reader = new Paint();
        reader.parseFile(path);
        reader.loopAndDraw(simpleGraphics);

    }
}
