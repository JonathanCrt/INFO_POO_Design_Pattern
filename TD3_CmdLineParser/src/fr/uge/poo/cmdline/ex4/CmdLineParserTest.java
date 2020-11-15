package fr.uge.poo.cmdline.ex4;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("static-method")
public class CmdLineParserTest {

    /******************************** Exercise 1 ***********************************/


    @Test
    public void processShouldFailFastOnNullArgument() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.process(null));
    }

    @Test
    public void checkRequiredNotNullOnLambda() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.registerOption("-legacy", null));
    }

    @Test
    public void checkRequiredNotNullOnOptionName() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.registerOption(null, () -> {
        }));
    }

    @Test
    public void checkRequiredNotNullOnOptionRegister() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.registerOption(null, null));
    }

    @Test
    public void throwsExceptionIfRegisterTwiceOption() {
        var optionsBuilder = new PaintOptionsBuilder();
        var parser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            parser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
            parser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
            parser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
            optionsBuilder.build();
        });
    }

    @Test
    public void checkLegacyValueTrue() {
        var optionsBuilder = new PaintOptionsBuilder();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy"};
        parser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
        parser.process(arguments);
        var options = optionsBuilder.build();
        assertTrue(options.isLegacy());
    }

    @Test
    public void checkBorderedValueTrue() {
        var optionsBuilder = new PaintOptionsBuilder();
        var parser = new CmdLineParser();
        String[] arguments = {"-with-borders"};
        parser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
        parser.process(arguments);
        var options = optionsBuilder.build();
        assertTrue(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalse() {
        var optionsBuilder = new PaintOptionsBuilder();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy", "-with-borders"};
        parser.registerOption("-with-borders", () -> optionsBuilder.setBordered(false));
        parser.process(arguments);
        var options = optionsBuilder.build();
        assertFalse(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalseThenTrue() {
        var optionsBuilder = new PaintOptionsBuilder();
        var parser = new CmdLineParser();
        String[] arguments = {"-no-borders", "-with-borders"};
        parser.registerOption("-no-borders", () -> optionsBuilder.setBordered(false));
        parser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
        parser.process(arguments);
        var options = optionsBuilder.build();
        assertTrue(options.isBordered());
    }

    @Test
    public void checkFilenames() {
        var optionsBuilder = new PaintOptionsBuilder();
        String[] arguments = {"-legacy", "-no-borders", "-with-borders", "filename1", "filename2"};
        var cmdParser = new fr.uge.poo.cmdline.ex2.CmdLineParser();
        cmdParser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> optionsBuilder.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> optionsBuilder.setBordered(false));
        var files = cmdParser.process(arguments);

        assertAll(
                () -> assertEquals(2, files.size()),
                () -> assertEquals(files.get(1)
                        .getFileName()
                        .toString(), "filename2")
        );
    }


    /******************************** Exercise 2 ***********************************/


    @Test
    public void shouldThrowsNullPointerIfNullConsumer() {
        var cmdParser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> cmdParser.registerWithParameter("window-name", null));
    }

    @Test
    public void shouldThrowsNullPointerIfNullOptionName() {
        var cmdParser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> cmdParser.registerWithParameter(null, code -> {
        }));
    }


    @Test
    public void checkRegisterOptionParametersValue() {
        var optionsBuilder = new PaintOptionsBuilder();
        String[] arguments = {"-legacy", "-no-borders", "-border-width", "500", "-windows-name", "Area", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerWithParameter("-border-width", size -> optionsBuilder.setBorderWidth(Integer.parseInt(size)));
        cmdParser.registerWithParameter("-windows-name", optionsBuilder::setWindowName);
        cmdParser.process(arguments);
        var options = optionsBuilder.build();
        assertAll(
                () -> assertEquals(500, options.isBorderWidth()),
                () -> assertEquals("Area", options.isWindowName())
        );

    }

    @Test
    public void shouldThrowsExceptionIfRegisterTwiceOptionWithArguments() {
        var optionsBuilder = new PaintOptionsBuilder();
        var cmdLineParser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            cmdLineParser.registerWithParameter("-border-width", width -> optionsBuilder.setBorderWidth(Integer.parseInt(width)));
            cmdLineParser.registerWithParameter("-border-width", width -> optionsBuilder.setBorderWidth(Integer.parseInt(width)));
        });
    }

    @Test
    public void checkRegisterWithAndWithoutParameter() {
        var optionsBuilder = new PaintOptionsBuilder();
        var cmdParser = new CmdLineParser();
        String[] arguments = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "800"};

        cmdParser.registerOption("-legacy", () ->  optionsBuilder.setLegacy(true));
        cmdParser.registerWithParameter("-border-width", width ->  optionsBuilder.setBorderWidth(Integer.parseInt(width)));
        cmdParser.registerWithParameter("-window-name",  optionsBuilder::setWindowName);

        var files = cmdParser.process(arguments);
        var options = optionsBuilder.build();
        assertAll(
                () -> assertEquals(800, options.isBorderWidth()),
                () -> assertEquals("Area", options.isWindowName()),
                () -> assertEquals(2, files.size()),
                () -> assertTrue(options.isLegacy())
        );
    }

    @Test
    public void checkRegisterWithWindowNameValue() {
        var cmdLineParser = new CmdLineParser();
        var optionsBuilder = new PaintOptionsBuilder();
        String[] args  = {"-window-name", "Area", "draw1.txt", "draw2.txt"};
        cmdLineParser.registerWithParameter("-window-name", optionsBuilder::setWindowName);
        cmdLineParser.process(args);
        var options = optionsBuilder.build();
        assertEquals("Area", options.isWindowName());
    }

    @Test
    public void shouldThrowsIllegalStateExceptionIfBadRegisterWithoutParameter() {
        var optionsBuilder = new PaintOptionsBuilder();
        var cmdLineParser = new CmdLineParser();
        String[] arguments = {"-legacy", "filename1", "filename2", "-window-name"};

        cmdLineParser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
        cmdLineParser.registerWithParameter("-window-name", optionsBuilder::setWindowName);

        assertThrows(IllegalStateException.class, () -> cmdLineParser.process(arguments));
    }

    @Test
    public void checkFileNameWhenRegisterWithParameter() {
        var cmdLineParser = new CmdLineParser();
        var optionsBuilder = new PaintOptionsBuilder();
        String[] args = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "250"};
        cmdLineParser.registerOption("-legacy", () -> optionsBuilder.setLegacy(true));
        cmdLineParser.registerWithParameter("-border-width", width -> optionsBuilder.setBorderWidth(Integer.parseInt(width)));
        cmdLineParser.registerWithParameter("-window-name", optionsBuilder::setWindowName);

        var listOfFiles = cmdLineParser.process(args);
        assertAll(
                () -> assertEquals("draw1.txt", listOfFiles.get(0).getFileName().toString()),
                () -> assertEquals(2, listOfFiles.size()),
                () -> assertEquals("draw2.txt", listOfFiles.get(1).getFileName().toString())
        );
    }
}