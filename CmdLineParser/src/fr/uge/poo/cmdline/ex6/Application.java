package fr.uge.poo.cmdline.ex6;

public class Application {

    public static void main(String[] args)  {
        var optionsBuilder = new PaintSettings.PaintSettingsBuilder();
        String[] arguments = {"-legacy", "-min-size", "200", "300", "filename1", "filename2"};
        var cmdLineParser = new CmdLineParser();

        // PASS MANDATORY PARAMETER AND THE SETTER ARE ONLY FOR OPTIONAL PARAMETER
        var optionOne = new Option.OptionBuilder("-legacy", 0, parameters -> optionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var optionTwo = new Option.OptionBuilder("-min-size", 2, parameters -> {
            optionsBuilder.setWindowWidth(Integer.parseInt(parameters.get(0)));
            optionsBuilder.setWindowHeight(Integer.parseInt(parameters.get(1)));
        })
                .isMandatory()
                .build();

        var optionThree = new Option.OptionBuilder("-toto", 0, parameters -> optionsBuilder.setLegacy(true))
                .conflictWith("-legaçy")
                .build();

        cmdLineParser.addOption(optionOne);
        cmdLineParser.addOption(optionTwo);
        cmdLineParser.addOption(optionThree);

        var files = cmdLineParser.process(arguments);
        files.forEach(System.out::println);

        var options = optionsBuilder.build();
        System.out.println(options);

    }
}