package fr.uge.poo.cmdline.ex4;

public class Application {

    public static void main(String[] args) throws ProcessException {
        var optionsBuilder = new PaintOptions.PaintOptionsBuilder();
        String[] arguments = {"-legacy","-min-size", "200", "300", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();

        //passez les parametre obligatoire et les setter sont que pour les parametre optionnel
        var opt1 = new Option.OptionBuilder("-legacy", 0, (parameters) -> optionsBuilder.setLegacy(true))
                .required()
                .build();

        var opt2 = new Option.OptionBuilder("-min-size", 2, (parameters) -> {
            optionsBuilder.setWindowWidth(Integer.parseInt(parameters.get(0)));
            optionsBuilder.setWindowHeight(Integer.parseInt(parameters.get(1)));
        })
                .required()
                .build();

        var opt3 = new Option.OptionBuilder("-toto", 0, (parameters) -> optionsBuilder.setLegacy(true))
                .build();

        cmdParser.registerOption(opt1);
        cmdParser.registerOption(opt2);
        cmdParser.registerOption(opt3);

        var files = cmdParser.process(arguments);
        // files.forEach(p -> System.out.println(p));

        var options = optionsBuilder.build();
        //System.out.println(options);

    }
}