package fr.uge.poo.paint.ex8;

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
        var shapesList = ContainerShape.from(path);

        /*
        if (args.length < 1) {
            System.out.println("Usage : java Paint --legacy");
            return;
        }
         */

        var canvas = args.length == 1 && args[0].equals("legacy") ? new CoolGraphicsAdapter(800, 800) : new SimpleGraphicsAdapter(800, 800);
        canvas.clear(EColor.WHITE);
        shapesList.loopAndDraw(canvas, EColor.BLACK);
        canvas.waitOnClick((x, y) -> shapesList.changeColor(x, y, EColor.ORANGE, canvas));

    }
}
