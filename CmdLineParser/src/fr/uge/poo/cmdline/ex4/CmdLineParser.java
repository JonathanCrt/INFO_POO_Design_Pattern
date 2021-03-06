package fr.uge.poo.cmdline.ex4;

import java.nio.file.Path;
import java.util.*;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptionsWithParameterUniqueMap = new HashMap<>();

    private final Set<String> mandatoryOptionsSet = new HashSet<>();

    private final Set<String> processedOptionsSet = new HashSet<>();


    public void addFlag(Option option) {
        checkIfArgumentsAreNull(option);
        if (registeredOptionsWithParameterUniqueMap.get(option.name) != null) {
            throw new IllegalStateException("Argument already exists");
        }
        registeredOptionsWithParameterUniqueMap.put(option.name, option);
        if (option.isMandatory) {
            mandatoryOptionsSet.add(option.name);
        }
    }

    public List<Path> process(String[] argumentsLineCommand) throws ProcessException {
        checkIfArgumentsAreNull(argumentsLineCommand);
        var paths = new ArrayList<Path>();
        var iterator = Arrays.stream(argumentsLineCommand).iterator();

        while (iterator.hasNext()) {
            var nextValue = iterator.next(); // map key
            if (!nextValue.startsWith("-")) { //  can be name file or argument of option
                paths.add(Path.of(nextValue));
                continue;
            }

            var currentOption = registeredOptionsWithParameterUniqueMap.get(nextValue);
            if (currentOption == null) {
                throw new IllegalStateException("Error this option doesn't exist");
            }

            /**
             var optionArguments = IntStream
             .range(0, currentOption.numberArguments)
             .mapToObj(arg -> iterator.next()) // mapToObj more specific
             .collect(Collectors.toList());
             **/
            // get arguments of one option
            var optionsArguments = new ArrayList<String>();
            for (var i = 0; i < currentOption.numberArguments; i++) {
                var value = iterator.next();
                if (value.startsWith("-")) {
                    throw new IllegalArgumentException(value + " is not an argument ! (Option ?)");
                }
                optionsArguments.add(value);
            }
            currentOption.acListConsumer.accept(optionsArguments);
            this.processedOptionsSet.add(currentOption.name);
        }

        var causesExceptionList = new ArrayList<String>();
        for (var cause : mandatoryOptionsSet) {
            if (!processedOptionsSet.contains(cause)) {
                causesExceptionList.add(cause);
            }
        }

        // if list is not empty we have causes of exception during process
        if (!causesExceptionList.isEmpty()) {
            throw new ProcessException(causesExceptionList);
        }

        return paths;

    }

    private static void checkIfArgumentsAreNull(Object... args) {
        for (Object obj : args) {
            Objects.requireNonNull(obj);
        }
    }


}