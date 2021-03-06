package fr.uge.poo.paint.ex5;

import fr.uge.poo.simplegraphics.SimpleGraphics;

import java.awt.*;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Si on stocke la couleur pour chaque figure dans un champ, et que l'on souhaite la modifier (noir -> orrange)
 * on va rendre notre type commun Shape mutable
 */
public class Paint {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get("C:/Users/jonat/Documents/GitHub/INFO_POO_Design_Pattern/TD1_UGE_Paint/src/fr/uge/poo/paint/file/draw2.txt");

        var area = new SimpleGraphics("figure", 800, 600);
        area.clear(Color.white);

        var shapesList = ContainerShape.from(path);
        shapesList.loopAndDraw(area);
        area.waitForMouseEvents((x, y) -> shapesList.changeColor(x, y, Color.ORANGE, area));

    }
}
