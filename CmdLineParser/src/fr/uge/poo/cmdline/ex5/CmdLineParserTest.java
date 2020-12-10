package fr.uge.poo.cmdline.ex5;

import fr.uge.poo.cmdline.ex4.ProcessException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CmdLineParserTest {
    @Test
    public void checkOptWithMoreOneAliases() throws ProcessException {
        var optionsBuilder = new PaintSettings.PaintSettingsBuilder();
        String[] arguments = {"-legacy", "-min-size", "200", "300", "filename1", "filename2", "--h2", "localhost"};
        var cmdParser = new CmdLineParser();

        var option1 = new Option.OptionBuilder("-legacy", 0, (parameters) -> optionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var option2 = new Option.OptionBuilder("-min-size", 2, (parameters) -> optionsBuilder
                .setWindowWidth(Integer.parseInt(parameters.get(0)))
                .setWindowHeight(Integer.parseInt(parameters.get(1))))
                .isMandatory()
                .build();

        var option3 = new Option.OptionBuilder("-host", 1, (parameters) -> optionsBuilder.setWindowName(parameters.get(0)))
                .addAlias("--h")
                .addAlias("--h2")
                .addAlias("--h3")
                .build();

        cmdParser.addOption(option1);
        cmdParser.addOption(option2);
        cmdParser.addOption(option3);

        cmdParser.process(arguments);
        var option = optionsBuilder.build();
        assertEquals(200, option.getWindowWidth()); //500 is default value
        assertEquals(300, option.getWindowHeight());
        assertEquals("localhost", option.getWindowName());
    }
}
