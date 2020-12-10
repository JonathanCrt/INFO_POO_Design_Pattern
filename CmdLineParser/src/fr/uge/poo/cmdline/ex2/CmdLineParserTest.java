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
        assertThrows(NullPointerException.class, () -> parser.addFlag("-legacy", null));
    }

    @Test
    public void checkRequiredNotNullOnOptionName() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.addFlag(null, () -> {
        }));
    }

    @Test
    public void checkRequiredNotNullOnOptionRegister() {
        var parser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> parser.addFlag(null, null));
    }

    @Test
    public void throwsExceptionIfRegisterTwiceOption() {
        var options = new Application.PaintSettings();
        var parser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            parser.addFlag("-legacy", () -> options.setLegacy(true));
            parser.addFlag("-with-borders", () -> options.setBordered(true));
            parser.addFlag("-with-borders", () -> options.setBordered(true));
        });
    }

    @Test
    public void checkLegacyValueTrue() {
        var options = new Application.PaintSettings();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy"};
        parser.addFlag("-legacy", () -> options.setLegacy(true));
        parser.process(arguments);
        assertTrue(options.isLegacy());
    }

    @Test
    public void checkBorderedValueTrue() {
        var options = new Application.PaintSettings();
        var parser = new CmdLineParser();
        String[] arguments = {"-with-borders"};
        parser.addFlag("-with-borders", () -> options.setBordered(true));
        parser.process(arguments);
        assertTrue(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalse() {
        var options = new Application.PaintSettings();
        var parser = new CmdLineParser();
        String[] arguments = {"-legacy", "-with-borders"};
        parser.addFlag("-with-borders", () -> options.setBordered(false));
        parser.process(arguments);
        assertFalse(options.isBordered());
    }

    @Test
    public void checkBorderedValueFalseThenTrue() {
        var options = new Application.PaintSettings();
        var parser = new CmdLineParser();
        String[] arguments = {"-no-borders", "-with-borders"};
        parser.addFlag("-no-borders", () -> options.setBordered(false));
        parser.addFlag("-with-borders", () -> options.setBordered(true));
        parser.process(arguments);
        assertTrue(options.isBordered());
    }

    @Test
    public void checkFilenames() {
        var options = new Application.PaintSettings();
        String[] arguments = {"-legacy", "-no-borders", "-with-borders", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
        cmdParser.addFlag("-with-borders", () -> options.setBordered(true));
        cmdParser.addFlag("-no-borders", () -> options.setBordered(false));
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
        assertThrows(NullPointerException.class, () -> cmdParser.addOptionWithOneParameter("window-name", null));
    }

    @Test
    public void shouldThrowsNullPointerIfNullOptionName() {
        var cmdParser = new CmdLineParser();
        assertThrows(NullPointerException.class, () -> cmdParser.addOptionWithOneParameter(null, code -> {
        }));
    }


    @Test
    public void checkRegisterOptionParametersValue() {
        var options = new Application.PaintSettings();
        String[] arguments = {"-legacy", "-no-borders", "-border-width", "500", "-windows-name", "Area", "filename1", "filename2"};
        var cmdParser = new CmdLineParser();
        cmdParser.addOptionWithOneParameter("-border-width", size -> options.setBorderWidth(Integer.parseInt(size)));
        cmdParser.addOptionWithOneParameter("-windows-name", options::setWindowName);
        cmdParser.process(arguments);

        assertAll(
                () -> assertEquals(500, options.getBorderWidth()),
                () -> assertEquals("Area", options.getWindowName())
        );

    }

    @Test
    public void shouldThrowsExceptionIfRegisterTwiceOptionWithArguments() {
        var options = new Application.PaintSettings();
        var cmdLineParser = new CmdLineParser();
        assertThrows(IllegalStateException.class, () -> {
            cmdLineParser.addOptionWithOneParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
            cmdLineParser.addOptionWithOneParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
        });
    }

    @Test
    public void checkRegisterWithAndWithoutParameter() {
        var options = new Application.PaintSettings();
        var cmdParser = new CmdLineParser();
        String[] arguments = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "800"};

        cmdParser.addFlag("-legacy", () -> options.setLegacy(true));
        cmdParser.addOptionWithOneParameter("-border-width", width -> options.setBorderWidth(Integer.parseInt(width)));
        cmdParser.addOptionWithOneParameter("-window-name", options::setWindowName);

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
        var paint = new Application.PaintSettings();
        String[] args  = {"-window-name", "Area", "draw1.txt", "draw2.txt"};
        cmdLineParser.addOptionWithOneParameter("-window-name", paint::setWindowName);
        cmdLineParser.process(args);
        assertEquals("Area", paint.getWindowName());
    }

    @Test
    public void shouldThrowsIllegalStateExceptionIfBadRegisterWithoutParameter() {
        var options = new Application.PaintSettings();
        var cmdLineParser = new CmdLineParser();
        String[] arguments = {"-legacy", "filename1", "filename2", "-window-name"};

        cmdLineParser.addFlag("-legacy", () -> options.setLegacy(true));
        cmdLineParser.addOptionWithOneParameter("-window-name", options::setWindowName);

        assertThrows(IllegalStateException.class, () -> cmdLineParser.process(arguments));
    }

    @Test
    public void checkFileNameWhenRegisterWithParameter() {
        var cmdLineParser = new CmdLineParser();
        var paintOptions = new Application.PaintSettings();
        String[] args = {"-legacy", "-window-name", "Area", "draw1.txt", "draw2.txt", "-border-width", "250"};
        cmdLineParser.addFlag("-legacy", () -> paintOptions.setLegacy(true));
        cmdLineParser.addOptionWithOneParameter("-border-width", width -> paintOptions.setBorderWidth(Integer.parseInt(width)));
        cmdLineParser.addOptionWithOneParameter("-window-name", paintOptions::setWindowName);

        var listOfFiles = cmdLineParser.process(args);
        assertAll(
                () -> assertEquals("draw1.txt", listOfFiles.get(0).getFileName().toString()),
                () -> assertEquals(2, listOfFiles.size()),
                () -> assertEquals("draw2.txt", listOfFiles.get(1).getFileName().toString())
        );
    }
}