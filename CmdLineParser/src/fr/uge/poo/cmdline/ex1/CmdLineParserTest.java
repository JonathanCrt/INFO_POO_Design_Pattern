package fr.uge.poo.cmdline.ex1;

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
}