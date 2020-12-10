package fr.uge.poo.cmdline.ex3;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;

/**
 * we need to store Iterator<String> into unique map
 * on itére sur tous les arguments du tableau
 * pas de type commun entre Runnable et Consumer (abstrait interface fonctionelle)
 */
public class CmdLineParser {

    private final HashMap<String, Consumer<Iterator<String>>> registeredOptionsWithParameterUniqueMap = new HashMap<>();

    public void addFlag(String option, Runnable code) {
        checkIfArgumentsAreNull(option, code);
        if (registeredOptionsWithParameterUniqueMap.containsKey(option)) {
            throw new IllegalStateException(option + " already exists");
        }
        registeredOptionsWithParameterUniqueMap.put(option, __ -> code.run()); // __ -> on se fiche du paramètre
    }

    /**
     * void method
     * register an option with given parameter
     *
     * @param optionName name of option
     * @param code       action to do
     */
    public void addOptionWithOneParameter(String optionName, Consumer<String> code) {
        checkIfArgumentsAreNull(optionName, code);
        if (registeredOptionsWithParameterUniqueMap.containsKey(optionName)) {
            throw new IllegalStateException(optionName + "already exists");
        }
        registeredOptionsWithParameterUniqueMap.put(optionName, stringIterator -> {
            if (!stringIterator.hasNext()) {
                throw new IllegalStateException("Missing option argument");
            }
            code.accept(stringIterator.next());
        });
    }


    public List<Path> process(String...argumentsLineCommand) {
        checkIfArgumentsAreNull(argumentsLineCommand);
        var paths = new ArrayList<Path>();


        Iterator<String> iterator = Arrays
                .stream(argumentsLineCommand)
                .iterator();

        while (iterator.hasNext()) {
            var nextOptionName = iterator.next(); // map key
            var mapConsumerValue = this.registeredOptionsWithParameterUniqueMap.get(nextOptionName);
            if (mapConsumerValue == null) { // if null , is name of file
                paths.add(Path.of(nextOptionName));
            } else {
                mapConsumerValue.accept(iterator); // execute le code du consumeur
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