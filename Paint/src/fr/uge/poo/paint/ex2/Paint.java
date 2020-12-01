package fr.uge.poo.paint.ex2;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class Paint {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:/Users/jonat/Documents/GitHub/INFO_POO_Design_Pattern/TD1_UGE_Paint/src/fr/uge/poo/paint/file/draw1.txt");
        var simpleGraphics = new SimpleGraphics("figure", 800, 600);
        simpleGraphics.clear(Color.white);
        var linesList = new ArrayList<Line>();

        try(Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                var tokens = line.split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);

                var extractedLine = new Line(x1, x2, y1, y2);
                linesList.add(extractedLine);
            });
        }
        linesList.forEach(line -> simpleGraphics.render(line::draw));
    }
}
