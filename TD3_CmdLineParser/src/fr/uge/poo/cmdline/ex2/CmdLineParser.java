package fr.uge.poo.cmdline.ex2;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CmdLineParser {

    private final HashMap<String, Runnable> registeredOptions = new HashMap<>();
    private final HashMap<String, Consumer<String>> registeredOptionsWithParameters = new HashMap<>();


    public void registerOption(String option, Runnable code) {
        checkIfArgumentsAreNull(option, code);
        if (registeredOptions.containsKey(option)) {
            throw new IllegalStateException(option + " already exists");
        }
        registeredOptions.put(option, code);
    }

    public void registerWithParameter(String optionName, Consumer<String> arg) {
        checkIfArgumentsAreNull(optionName, arg);
        if (registeredOptionsWithParameters.containsKey(optionName)) {
            throw new IllegalStateException(optionName + "already exists");
        }
        this.registeredOptionsWithParameters.put(optionName, arg);
    }


    public List<Path> process(String[] arguments) {
        Objects.requireNonNull(arguments);
        ArrayList<Path> paths = new ArrayList<>();
        for (var i = 0; i < arguments.length; i++) {
            if (registeredOptions.containsKey(arguments[i])) {
                registeredOptions
                        .get(arguments[i])
                        .run();
            } else if (registeredOptionsWithParameters.containsKey(arguments[i])) {
                registeredOptionsWithParameters.get(arguments[i]).accept(arguments[i + 1]);
                i++;// skipper l'args recupérer
            } else {
                paths.add(Path.of(arguments[i]));
            }
        }
        return paths;

    }

    private static void checkIfArgumentsAreNull(Object... args) {
        for (Object obj : args) {
            Objects.requireNonNull(obj);
        }
    }


}