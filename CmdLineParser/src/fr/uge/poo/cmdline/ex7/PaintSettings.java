package fr.uge.poo.cmdline.ex7;

public class PaintSettings {

    public static class PaintSettingsBuilder {

        private boolean legacy = false;
        private boolean bordered = true;
        private int borderWidth;
        private int windowWidth;
        private int windowHeight;

        private String windowName;

        public PaintSettingsBuilder setLegacy(boolean legacy) {
            this.legacy = legacy;
            return this;
        }

        public PaintSettingsBuilder setBordered(boolean bordered) {
            this.bordered = bordered;
            return this;
        }

        public PaintSettingsBuilder setBorderWidth(int width) {
            this.borderWidth = width;
            return this;
        }

        public PaintSettingsBuilder setWindowName(String windowName) {
            this.windowName = windowName;
            return this;
        }

        public PaintSettingsBuilder setWindowWidth(int windowWidth) {
            this.windowWidth = windowWidth;
            return this;
        }

        public PaintSettingsBuilder setWindowHeight(int windowHeight) {
            this.windowHeight = windowHeight;
            return this;
        }


        public PaintSettings build() {
            return new PaintSettings(this);
        }
    }

    private final boolean legacy;
    private final boolean bordered;

    private final int borderWidth;
    private final int windowWidth;
    private final int windowHeight;
    private final String windowName;

    /**
     * Nous oblige à faire un contructeur d'au moins visibilité package (inconvénient)
     * A l'intérieur de PaintOptions, la vsibilité du constructeur est privé
     * Si on veut créer une interface de Builder , on doit créer le Builder en dehors de la classe
     */
    private PaintSettings(PaintSettingsBuilder builder) {
        this.legacy = builder.legacy;
        this.bordered = builder.bordered;
        this.borderWidth = builder.borderWidth;
        this.windowWidth = builder.windowWidth;
        this.windowHeight = builder.windowHeight;
        this.windowName = builder.windowName;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public boolean isBordered() {
        return bordered;
    }

    public int getBorderWidth() {
        return borderWidth;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public String getWindowName() {
        return windowName;
    }

    @Override
    public String toString() {
        return "PaintOption [ bordered = " + bordered + ", legacy = " + legacy + " ]";
    }

}

