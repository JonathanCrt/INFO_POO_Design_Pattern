package fr.uge.poo.cmdline.ex3;


/**
 * Nous utilisons le patron de conception Builder dans la mesure ou il nous permet de séparer la responsabilité de
 * création de l'objet PaintOptions
 */
public class PaintOptionsBuilder {

    private boolean legacy = false;
    private boolean bordered = true;
    private int borderWidth;
    private String windowName;

    public PaintOptionsBuilder setLegacy(boolean legacy) {
        this.legacy = legacy;
        return this;
    }

    public PaintOptionsBuilder setBordered(boolean bordered) {
        this.bordered = bordered;
        return this;
    }

    public PaintOptionsBuilder setBorderWidth(int width) {
        this.borderWidth = width;
        return this;
    }

    public PaintOptionsBuilder setWindowName(String windowName) {
        this.windowName = windowName;
        return this;
    }

    public PaintOptions build() {
        return new PaintOptions(this.legacy, this.bordered, this.borderWidth, this.windowName);
    }

}
