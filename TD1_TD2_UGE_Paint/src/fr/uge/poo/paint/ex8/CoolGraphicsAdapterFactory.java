package fr.uge.poo.paint.ex8;

public class CoolGraphicsAdapterFactory implements CanvasFactory {

    @Override
    public Canvas createCanvas(int widthWindow, int heightWindow) {
        return new CoolGraphicsAdapter(widthWindow, heightWindow);
    }
}
