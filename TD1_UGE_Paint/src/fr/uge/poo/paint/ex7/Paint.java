package fr.uge.poo.paint.ex7;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Si on stocke la couleur pour chaque figure dans un champ, et que l'on souhaite la modifier (noir -> orrange)
 * on va rendre notre type commun Shape mutable
 */
public class Paint {

    private static final Logger logger = Logger.getLogger(Paint.class.getName());

    public static ContainerShape parseFile(Path path) throws IOException {
        var shapesList = new ContainerShape();
        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(line -> {
                String[] tokens = line.split(" ");
                int x1 = Integer.parseInt(tokens[1]);
                int y1 = Integer.parseInt(tokens[2]);
                int x2 = Integer.parseInt(tokens[3]);
                int y2 = Integer.parseInt(tokens[4]);

                switch (tokens[0]) {
                    case "line":
                        shapesList.add(new Line(x1, x2, y1, y2));
                        break;
                    case "ellipse":
                        shapesList.add(new Ellipse(x1, y1, x2, y2));
                        break;
                    case "rectangle":
                        shapesList.add(new Rectangle(x1, y1, x2, y2));
                        break;
                    default:

                        logger.info("This shape doesn't exist");
                }

            });
        }
        return shapesList;

    }

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:/Users/jonat/Documents/GitHub/INFO_POO_Design_Pattern/TD1_UGE_Paint/src/fr/uge/poo/paint/file/draw2.txt");

        var area = new SimpleGraphics("figure", 800, 600);
        area.clear(Color.white);

        var shapesList = Paint.parseFile(path);
        shapesList.loopAndDraw(area);
        area.waitForMouseEvents((x, y) -> {
            shapesList.changeColor(x, y, Color.ORANGE, area);
        });

    }
}
