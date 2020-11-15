package fr.uge.poo.cmdline.ex3;

public class Application {

    public static void main(String[] args) {
        var optionsBuilder = new PaintOptionsBuilder();
        String[] arguments = {"-legacy", "-no-borders", "-border-width", "500", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> optionsBuilder.setBordered(false));

        cmdParser.registerWithParameter("-border-width", size -> {
            optionsBuilder.setBorderWidth(Integer.parseInt(size));
        });
        var result = cmdParser.process(arguments);
        optionsBuilder.build();
        result.forEach(System.out::println);

        System.out.println(optionsBuilder.toString());
    }
}