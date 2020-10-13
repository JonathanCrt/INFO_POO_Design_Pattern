package fr.uge.poo.paint.ex8;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ServiceLoader;

/**
 * Si on stocke la couleur pour chaque figure dans un champ, et que l'on souhaite la modifier (noir -> orrange)
 * on va rendre notre type commun Shape mutable
 */
public class Paint {

    public static void main(String[] args) throws IOException {
        Path path = Paths.get(args[0]);
        var shapesList = ContainerShape.from(path);

        ServiceLoader<CanvasFactory> loaderCanvasFactory = ServiceLoader.load(fr.uge.poo.paint.ex8.CanvasFactory.class);
        var canvas = loaderCanvasFactory.findFirst()
                .map(shape -> shape.createCanvas(800,800))
                .orElse(new SimpleGraphicsAdapter(800, 800));

        canvas.clear(EColor.WHITE);
        shapesList.loopAndDraw(canvas, EColor.BLACK);
        canvas.waitOnClick((x, y) -> shapesList.changeColor(x, y, EColor.ORANGE, canvas));

    }
}
