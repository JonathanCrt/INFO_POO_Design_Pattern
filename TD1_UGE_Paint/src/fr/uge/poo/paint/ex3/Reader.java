package fr.uge.poo.paint.ex3;

import fr.uge.poo.paint.ex4.AbstractShape;
import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Reader implements Shape {

    private List<Shape> shapes =  new ArrayList<>();


    public void parseFile(Path path) throws IOException {
        try(Stream lines = Files.lines(path)) {
            //lines.forEach(line -> System.out.println(line));
            lines.forEach(line -> {
                String[] tokens = line.toString().split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);
                
            });

            shapes.add(new Line)
        }
    }


    public static void main(String[] args) throws IOException {
        Path path = Paths.get("src/fr/uge/poo/paint/ex2/draw1.txt");
        var simpleGraphics = new SimpleGraphics("figure", 800, 600);
        simpleGraphics.clear(Color.white);
        var linesList = new ArrayList<Line>();

        for(Line l : linesList) {
            simpleGraphics.render(graphics2D -> l.draw(graphics2D));
        }
    }

    @Override
    public void draw(Graphics2D graphics2D, String typeGraphic) {

    }
}
