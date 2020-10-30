package fr.uge.poo.paint.ex9;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Si on stocke la couleur pour chaque figure dans un champ, et que l'on souhaite la modifier (noir -> orrange)
 * on va rendre notre type commun Shape mutable
 */
public class Paint {

    public static void main(String[] args) throws IOException {

        if (args.length < 1) {
            System.out.println("Usage : java Paint -legacy");
            return;
        }

        Path path = Paths.get(args[0]);
        var shapesList = ContainerShape.from(path);

        var canvas = args.length == 2 && args[1].equals("-legacy") ? new SimpleGraphicsAdapterWithoutListOfConsumer(800, 800) : new CoolGraphicsAdapter(800, 800);
        canvas.clear(EColor.WHITE);
        shapesList.loopAndDraw(canvas, EColor.BLACK);
        canvas.paint();
        canvas.waitOnClick((x, y) -> shapesList.changeColor(x, y, EColor.ORANGE, canvas));
    }
}
