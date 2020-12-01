package fr.uge.poo.cmdline.ex7;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CmdLineParserTest {
    @Test
    public void processRequiredOption() {
        var cmdParser = new CmdLineParser();
        var option = new Option.OptionBuilder("-test", 0, l -> {
        }).isMandatory().build();
        cmdParser.addOption(option);
        cmdParser.addFlag("-test1", () -> {
        });
        String[] arguments = {"-test1", "a", "b"};
        assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }

    @Test
    public void processConflicts() {
        var cmdParser = new CmdLineParser();
        var option = new Option.OptionBuilder("-test", 0, l -> {
        }).conflictWith("-test1").build();
        cmdParser.addOption(option);
        var option2 = new Option.OptionBuilder("-test1", 0, l -> {
        }).build();
        cmdParser.addOption(option2);
        String[] arguments = {"-test", "-test1"};
        assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }

    @Test
    public void processConflicts2() {
        var cmdParser = new CmdLineParser();
        var option = new Option.OptionBuilder("-test", 0, l -> {
        }).conflictWith("-test1").build();
        cmdParser.addOption(option);
        var option2 = new Option.OptionBuilder("-test1", 0, l -> {
        }).build();
        cmdParser.addOption(option2);
        String[] arguments = {"-test1", "-test"};
        assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }

    @Test
    public void processConflictsAndAliases() {
        var cmdParser = new CmdLineParser();
        var option = new Option.OptionBuilder("-test", 0, l -> {
        }).conflictWith("-test2").build();
        cmdParser.addOption(option);
        var option2 = new Option.OptionBuilder("-test1", 0, l -> {
        }).addAlias("-test2").build();
        cmdParser.addOption(option2);
        String[] arguments = {"-test1", "-test"};
        assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }

    @Test
    public void processConflictsAndAliases2() {
        var cmdParser = new CmdLineParser();
        var option = new Option.OptionBuilder("-test", 0, l -> {
        }).conflictWith("-test2").build();
        cmdParser.addOption(option);
        var option2 = new Option.OptionBuilder("-test1", 0, l -> {
        }).addAlias("-test2").build();
        cmdParser.addOption(option2);
        String[] arguments = {"-test", "-test1"};
        assertThrows(ParseException.class, () -> cmdParser.process(arguments));
    }

    @Test
    public void processPolicyStandard() {
        var hosts = new ArrayList<String>();
        var cmdParser = new CmdLineParser();
        var optionHosts= new Option.OptionBuilder("-hosts",2, hosts::addAll).build();
        cmdParser.addOption(optionHosts);
        cmdParser.addFlag("-legacy",()->{});
        String[] arguments = {"-hosts","localhost","-legacy","file"};
        assertThrows(ParseException.class,()-> cmdParser.process(arguments,CmdLineParser.STANDARD));
    }


    @Test
    public void processPolicyRelaxed() {
        var hosts = new ArrayList<String>();
        var cmdParser = new CmdLineParser();
        var optionHosts= new Option.OptionBuilder("-hosts",2, hosts::addAll).build();
        cmdParser.addOption(optionHosts);
        cmdParser.addFlag("-legacy",()->{});
        String[] arguments = {"-hosts","localhost","-legacy","file"};
        cmdParser.process(arguments,CmdLineParser.RELAXED);
        assertEquals(1,hosts.size());
        assertEquals("localhost",hosts.get(0));
    }



    @Test
    public void processPolicyOldSchool() {
        var hosts = new ArrayList<String>();
        var cmdParser = new CmdLineParser();
        var optionHosts= new Option.OptionBuilder("-hosts",2, hosts::addAll).build();
        cmdParser.addOption(optionHosts);
        cmdParser.addFlag("-legacy",()->{});
        String[] arguments = {"-hosts","localhost","-legacy","file"};
        cmdParser.process(arguments,CmdLineParser.OLDSCHOOL);
        assertEquals(2,hosts.size());
        assertEquals("localhost",hosts.get(0));
        assertEquals("-legacy",hosts.get(1));
    }


}
