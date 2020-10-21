package fr.uge.poo.cmdline.ex1;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CmdLineParser {

    private final HashMap<String, Runnable> registeredOptions = new HashMap<>();

    public void registerOption(String option, Runnable code) {
        Objects.requireNonNull(option);
        Objects.requireNonNull(code);
        if (registeredOptions.containsKey(option)) {
            throw new IllegalStateException(option + " already existsZ");
        }
        registeredOptions.put(option, code);
    }


    public List<Path> process(String[] arguments) {
        Objects.requireNonNull(arguments);
        ArrayList<Path> paths = new ArrayList<>();
        for (String option : arguments) {
            if (registeredOptions.containsKey(option)) {
                registeredOptions//
                        .get(option)
                        .run();
            } else {
                paths.add(Path.of(option));
            }
        }
        return paths;

    }
}