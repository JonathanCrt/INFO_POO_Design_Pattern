package fr.uge.poo.cmdline.ex3;

public class Application {

    public static void main(String[] args) {
        var optionsBuilder = new PaintSettings.PaintSettingsBuilder();
        String[] arguments = {"-legacy", "-no-borders", "-border-width", "500", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.addFlag("-legacy", () -> optionsBuilder.setLegacy(true));
        cmdParser.addFlag("-with-borders", () -> optionsBuilder.setBordered(true));
        cmdParser.addFlag("-no-borders", () -> optionsBuilder.setBordered(false));

        cmdParser.addOptionWithOneParameter("-border-width", size -> {
            optionsBuilder.setBorderWidth(Integer.parseInt(size));
        });
        var result = cmdParser.process(arguments);
        optionsBuilder.build();
        result.forEach(System.out::println);

        System.out.println(optionsBuilder.toString());
    }
}