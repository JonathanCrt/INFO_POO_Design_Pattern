package fr.uge.poo.cmdline.ex2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


@SuppressWarnings("static-method")
public class CmdLineParserTest {

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
    

}