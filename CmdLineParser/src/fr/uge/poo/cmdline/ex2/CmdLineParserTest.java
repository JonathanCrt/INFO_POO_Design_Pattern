package fr.uge.poo.cmdline.ex2;

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
        var options = new Application.PaintOptions();
        var parser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            parser.registerOption("-legacy", () -> options.setLegacy(true));
            parser.registerOption("-with-borders", () -> options.setBordered(true));
            parser.registerOption("-with-borders", () -> options.setBordered(true));
        });
    }

    @Test
    public void checkLegacyValueTrue() {
        var options = new Application.PaintOptions();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy"};
        parser.registerOption("-legacy", () -> options.setLegacy(true));
        parser.process(arguments);
        assertTrue(options.isLegacy());
    }

    @Test
    public void checkBorderedValueTrue() {
        var options = new Application.PaintOptions();
        var parser = new CmdLineParser();
        String[] arguments = {"-with-borders"};
        parser.registerOption("-with-borders", () -> options.setBordered(true));
        parser.process(arguments);
        assertTrue(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalse() {
        var options = new Application.PaintOptions();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy", "-with-borders"};
        parser.registerOption("-with-borders", () -> options.setBordered(false));
        parser.process(arguments);
        assertFalse(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalseThenTrue() {
        var options = new Application.PaintOptions();
        var parser = new CmdLineParser();
        String[] arguments = {"-no-borders", "-with-borders"};
        parser.registerOption("-no-borders", () -> options.setBordered(false));
        parser.registerOption("-with-borders", () -> options.setBordered(true));
        parser.process(arguments);
        assertTrue(options.isBordered());
    }

    @Test
    public void checkFilenames() {
        var options = new Application.PaintOptions();
        String[] arguments = {"-legacy", "-no-borders", "-with-borders", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerOption("-with-borders", () -> options.setBordered(true));
        cmdParser.registerOption("-no-borders", () -> options.setBordered(false));
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
        var options = new Application.PaintOptions();
        String[] arguments = {"-legacy", "-no-borders", "-border-width", "500", "-windows-name", "Area", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.registerWithParameter("-border-width", size -> options.setBorderWidth(Integer.parseInt(size)));
        cmdParser.registerWithParameter("-windows-name", options::setWindowName);
        cmdParser.process(arguments);

        assertAll(
                () -> assertEquals(500, options.getBorderWidth()),
                () -> assertEquals("Area", options.getWindowName())
        );

    }

    @Test
    public void shouldThrowsExceptionIfRegisterTwiceOptionWithArguments() {
        var options = new Application.PaintOptions();
        var cmdLineParser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            cmdLineParser.registerWithParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
            cmdLineParser.registerWithParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
        });
    }

    @Test
    public void checkRegisterWithAndWithoutParameter() {
        var options = new Application.PaintOptions();
        var cmdParser = new CmdLineParser();
        String[] arguments = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "800"};

        cmdParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdParser.registerWithParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
        cmdParser.registerWithParameter("-window-name", options::setWindowName);

        var files = cmdParser.process(arguments);

        assertAll(
                () -> assertEquals(800, options.getBorderWidth()),
                () -> assertEquals("Area", options.getWindowName()),
                () -> assertEquals(2, files.size()),
                () -> assertTrue(options.isLegacy())
        );
    }

    @Test
    public void checkRegisterWithWindowNameValue() {
        var cmdLineParser = new CmdLineParser();
        var paint = new Application.PaintOptions();
        String[] args  = {"-window-name", "Area", "draw1.txt", "draw2.txt"};
        cmdLineParser.registerWithParameter("-window-name", paint::setWindowName);
        cmdLineParser.process(args);
        assertEquals("Area", paint.getWindowName());
    }

    @Test
    public void shouldThrowsIllegalStateExceptionIfBadRegisterWithoutParameter() {
        var options = new Application.PaintOptions();
        var cmdLineParser = new CmdLineParser();
        String[] arguments = {"-legacy", "filename1", "filename2", "-window-name"};

        cmdLineParser.registerOption("-legacy", () -> options.setLegacy(true));
        cmdLineParser.registerWithParameter("-window-name", options::setWindowName);

        assertThrows(IllegalStateException.class, () -> cmdLineParser.process(arguments));
    }

    @Test
    public void checkFileNameWhenRegisterWithParameter() {
        var cmdLineParser = new CmdLineParser();
        var paintOptions = new Application.PaintOptions();
        String[] args = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "250"};
        cmdLineParser.registerOption("-legacy", () -> paintOptions.setLegacy(true));
        cmdLineParser.registerWithParameter("-border-width", width -> paintOptions.setBorderWidth(Integer.parseInt(width)));
        cmdLineParser.registerWithParameter("-window-name", paintOptions::setWindowName);

        var listOfFiles = cmdLineParser.process(args);
        assertAll(
                () -> assertEquals("draw1.txt", listOfFiles.get(0).getFileName().toString()),
                () -> assertEquals(2, listOfFiles.size()),
                () -> assertEquals("draw2.txt", listOfFiles.get(1).getFileName().toString())
        );
    }
}