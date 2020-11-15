package fr.uge.poo.cmdline.ex3;

public class PaintOptions {

    private boolean legacy;
    private boolean bordered;

    private int borderWidth;
    private String windowName;


    public PaintOptions(boolean legacy, boolean bordered, int borderWidth, String windowName) {
        this.legacy = legacy;
        this.bordered = bordered;
        this.borderWidth = borderWidth;
        this.windowName = windowName;
    }

    public boolean isLegacy(){
        return legacy;
    }

    public boolean isBordered(){
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
    public String toString(){
        return "PaintOption [ bordered = "+bordered+", legacy = "+ legacy +" ]";
    }

}

