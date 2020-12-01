package fr.uge.poo.cmdline.ex4;

public class Application {

    public static void main(String[] args) throws ProcessException {
        var optionsBuilder = new PaintOptions.PaintOptionsBuilder();
        String[] arguments = {"-legacy", "-min-size", "200", "300", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();

        // PASS MANDATORY PARAMETER AND THE SETTER ARE ONLY FOR OPTIONAL PARAMETER
        var optionOne = new Option.OptionBuilder("-legacy", 0, (parameters) -> optionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var optionTwo = new Option.OptionBuilder("-min-size", 2, parameters -> {
            optionsBuilder.setWindowWidth(Integer.parseInt(parameters.get(0)));
            optionsBuilder.setWindowHeight(Integer.parseInt(parameters.get(1)));
        })
                .isMandatory()
                .build();

        var optionThree = new Option.OptionBuilder("-toto", 0, (parameters) -> optionsBuilder.setLegacy(true))
                .build();

        cmdParser.registerOption(optionOne);
        cmdParser.registerOption(optionTwo);
        cmdParser.registerOption(optionThree);

        var files = cmdParser.process(arguments);
        files.forEach(System.out::println);

        var options = optionsBuilder.build();
        System.out.println(options);

    }
}