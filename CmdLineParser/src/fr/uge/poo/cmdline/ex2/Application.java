package fr.uge.poo.cmdline.ex2;

public class Application {

    static class PaintSettings {
        private boolean legacy=false;
        private boolean bordered=true;
        private int borderWidth;
        private String windowName;

        public void setLegacy(boolean legacy) {
            this.legacy = legacy;
        }

        public boolean isLegacy(){
            return legacy;
        }

        public void setBordered(boolean bordered){
            this.bordered=bordered;
        }

        public boolean isBordered(){
            return bordered;
        }

        public int getBorderWidth() {
            return borderWidth;
        }

        public void setBorderWidth(int borderWidth) {
            this.borderWidth = borderWidth;
        }

        public String getWindowName() {
            return windowName;
        }

        public void setWindowName(String windowName) {
            this.windowName = windowName;
        }

        @Override
        public String toString() {
            return "PaintOptions{" +
                    "legacy=" + legacy +
                    ", bordered=" + bordered +
                    ", borderWidth=" + borderWidth +
                    ", windowName='" + windowName + '\'' +
                    '}';
        }
    }

    public static void main(String[] args) {
        var options = new PaintSettings();
        String[] arguments={"-legacy","-no-borders", "-border-width","500","filename1","filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
        cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
        cmdParser.addFlag("-with-borders", () -> options.setBordered(true));
        cmdParser.addFlag("-no-borders", () -> options.setBordered(false));

        cmdParser.addOptionWithOneParameter("-border-width", size -> {
            options.setBorderWidth(Integer.parseInt(size));
        });
        var result = cmdParser.process(arguments);
        result.forEach(rlt -> System.out.println(rlt));

        System.out.println(options.toString());

    }
}