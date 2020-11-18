package fr.uge.poo.cmdline.ex3;

public class PaintOptions {

    public static class PaintOptionsBuilder {

        private boolean legacy = false;
        private boolean bordered = true;
        private int borderWidth;
        private String windowName = null;

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
            if (this.windowName.isEmpty() || this.windowName.isBlank()) {
                throw new IllegalStateException();
            }
            return new PaintOptions(this.legacy, this.bordered, this.borderWidth, this.windowName);
        }
    }

    private boolean legacy;
    private boolean bordered;

    private int borderWidth;
    private String windowName = null;

    /**
     * Nous oblige à faire un contructeur d'au moins visibilité package (inconvénient)
     * A l'intérieur de PaintOptions, la vsibilité du constructeur est privé
     * Si on veut créer une interface de Builder , on doit créer le Builder en dehors de la classe
     *
     * @param legacy
     * @param bordered
     * @param borderWidth
     * @param windowName
     */
    PaintOptions(boolean legacy, boolean bordered, int borderWidth, String windowName) {
        this.legacy = legacy;
        this.bordered = bordered;
        this.borderWidth = borderWidth;
        this.windowName = windowName;
    }

    public boolean isLegacy() {
        return legacy;
    }

    public boolean isBordered() {
        return bordered;
    }

    public int isBorderWidth() {
        return borderWidth;
    }

    public void isBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    public String isWindowName() {
        return windowName;
    }

    public void setLegacy(boolean legacy) {
        this.legacy = legacy;
    }

    public void setBordered(boolean bordered) {
        this.bordered = bordered;
    }

    public void setWindowName(String windowName) {
        this.windowName = windowName;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }


    @Override
    public String toString() {
        return "PaintOption [ bordered = " + bordered + ", legacy = " + legacy + " ]";
    }



}

