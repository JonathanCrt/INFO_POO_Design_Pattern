package fr.uge.poo.cmdline.ex4;

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

    public void registerOption(String option, Runnable code) {
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
    public void registerWithParameter(String optionName, Consumer<String> code) {
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

        //this.registeredOptionsWithParameters.put(optionName, arg);
    }


    public List<Path> process(String[] argumentsLineCommand) {
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
        /*
        for (var i = 0; i < arguments.length; i++) {
            if (registeredOptions.containsKey(arguments[i])) {
                registeredOptions
                        .get(arguments[i])
                        .run();
            } else if (registeredOptionsWithParameters.containsKey(arguments[i])) {
                if(arguments.length < i+1) {
                    throw new IllegalStateException("Error : Missing parameter of option");
                }
                registeredOptionsWithParameters.get(arguments[i]).accept(arguments[i + 1]);
                i++;// skipper l'args recupérer
            } else {
                paths.add(Path.of(arguments[i]));
            }
        }
         */
        return paths;

    }

    private static void checkIfArgumentsAreNull(Object... args) {
        for (Object obj : args) {
            Objects.requireNonNull(obj);
        }
    }


}