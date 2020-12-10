package fr.uge.poo.cmdline.ex4;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("static-method")
public class CmdLineParserTest {

    // exercice 4
    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }

    @Test
    public void checkRequiredNullOnOption() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.addFlag(null));
    }

    @Test
    public void throwsExceptionIfRegisterTwiceOption() {
        var optionOne = new Option.OptionBuilder("-legacy", 0, code -> {
        }).build();
        var optionTwo = new Option.OptionBuilder("-legacy", 0, code -> {
        }).build();
        var parser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            parser.addFlag(optionOne);
            parser.addFlag(optionTwo);
        });
    }

    @Test
    public void checkLegacyValueTrue() throws ProcessException {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var option = new Option.OptionBuilder("-legacy", 0, ptOpt -> paintOptionsBuilder.setLegacy(true)).build();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy"};
        parser.addFlag(option);
        parser.process(arguments);
        var paintOptions = paintOptionsBuilder.build();
        assertTrue(paintOptions.isLegacy());
    }


    @Test
    public void checkBorderedValueTrue() throws ProcessException {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var option = new Option.OptionBuilder("-with-borders", 0, ptOpt -> paintOptionsBuilder.setBordered(true)).build();
        var parser = new CmdLineParser();
        String[] arguments = {"-with-borders"};
        parser.addFlag(option);
        parser.process(arguments);
        var paintOptions = paintOptionsBuilder.build();
        assertTrue(paintOptions.isBordered());
    }


    @Test
    public void checkCorrectWindowsProperties() throws ProcessException {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var optionOne = new Option.OptionBuilder("-window-width", 1, opt -> paintOptionsBuilder.setWindowWidth(500)).build();
        var optionTwo = new Option.OptionBuilder("-window-height", 1, opt -> paintOptionsBuilder.setWindowHeight(500)).build();
        var optionThree = new Option.OptionBuilder("-window-name", 1, opt -> paintOptionsBuilder.setWindowName("Area")).build();

        var parser = new CmdLineParser();
        String[] arguments = {"-window-width", "500", "-window-height", "500", "-window-name", "Area"};
        parser.addFlag(optionOne);
        parser.addFlag(optionTwo);
        parser.addFlag(optionThree);
        parser.process(arguments);
        var paintOptions = paintOptionsBuilder.build();
        assertAll(() -> {
            assertEquals(500, paintOptions.getWindowWidth());
            assertEquals(500, paintOptions.getWindowHeight());
            assertEquals("Area", paintOptions.getWindowName());
        });

    }

    @Test
    public void checkUnknownOption() {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var cmdLineParser = new CmdLineParser();
        var arguments = new String[]{"-legacy", "-window-width", "-unknown"};

        var optionOne = new Option.OptionBuilder("-legacy", 0, param -> paintOptionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var optionTwo = new Option.OptionBuilder("-max-height", 2, (parameters) -> paintOptionsBuilder
                .setWindowWidth(Integer.parseInt(parameters.get(0)))
                .setWindowHeight(Integer.parseInt(parameters.get(1))))
                .isMandatory()
                .build();

        cmdLineParser.addFlag(optionOne);
        cmdLineParser.addFlag(optionTwo);
        assertThrows(IllegalStateException.class, () -> cmdLineParser.process(arguments));

    }

    @Test
    public void checkMandatoryOption() {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var cmdLineParser = new CmdLineParser();
        var arguments = new String[]{"-myOption"};

        var option = new Option.OptionBuilder("-no-borders", 0, (parameters) -> paintOptionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        cmdLineParser.addFlag(option);
        assertThrows(IllegalStateException.class, () -> cmdLineParser.process(arguments));
    }

    @Test
    public void checkThrowsIllegalStateExceptionWhenOptionNameIsEmpty() {
        var cmdLineParser = new CmdLineParser();
        assertThrows(
                IllegalStateException.class, () -> cmdLineParser.addFlag(new Option.OptionBuilder("", 0, __ -> {
                }).build()));
    }


    @Test
    public void checkOptionWithTwoArguments() throws ProcessException {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var cmdLineParser = new CmdLineParser();
        String[] arguments = {"-no-borders", "-window-size", "800", "600", "data1", "data2"};

        var optionOne = new Option.OptionBuilder("-no-borders", 0, param -> paintOptionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var optionTwo = new Option.OptionBuilder("-window-size", 2, param -> paintOptionsBuilder
                .setWindowWidth(Integer.parseInt(param.get(0)))
                .setWindowHeight(Integer.parseInt(param.get(1))))
                .isMandatory()
                .build();

        cmdLineParser.addFlag(optionOne);
        cmdLineParser.addFlag(optionTwo);
        cmdLineParser.process(arguments);
        var option = paintOptionsBuilder.build();
        assertEquals(800, option.getWindowWidth());
        assertEquals(600, option.getWindowHeight());

    }

    @Test
    public void checkThrowsIllegalArgumentExceptionWhenPassOptionInArgumentOfOption() {
        var paintOptionsBuilder = new PaintSettings.PaintSettingsBuilder();
        var cmdLineParser = new CmdLineParser();
        String[] arguments = {"-legacy", "-window-name", "-xvtz"};

        var optionOne = new Option.OptionBuilder("-legacy", 0, parameters -> paintOptionsBuilder.setLegacy(true))
                .isMandatory()
                .build();

        var optionTwo = new Option.OptionBuilder("-window-name", 1, parameter -> paintOptionsBuilder
                .setWindowName(parameter.get(0)))
                .isMandatory()
                .build();

        cmdLineParser.addFlag(optionOne);
        cmdLineParser.addFlag(optionTwo);
        assertThrows(IllegalArgumentException.class, () -> cmdLineParser.process(arguments));
    }


}