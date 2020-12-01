package fr.uge.poo.cmdline.ex5;

import fr.uge.poo.cmdline.ex4.ProcessException;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptionsWithParameterUniqueMap = new HashMap<>();

    private final Set<String> mandatoryOptionsSet = new HashSet<>();

    private final Set<String> processedOptionsSet = new HashSet<>();


    public void registerOption(Option option) {
        checkIfArgumentsAreNull(option);
        if (registeredOptionsWithParameterUniqueMap.get(option.name) != null) {
            throw new IllegalStateException("Argument already exists");
        }
        registeredOptionsWithParameterUniqueMap.put(option.name, option);
        if (option.isMandatory) {
            mandatoryOptionsSet.add(option.name);
        }
    }

    public String usage() {
        return registeredOptionsWithParameterUniqueMap
                .values()
                .stream()
                .sorted(Comparator.comparing(opt -> opt.name))
                .filter(opt -> !opt.documentation.isEmpty())
                .map(option -> option.name + " : " + option.documentation)
                .collect(Collectors.joining("\n"));
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

            var optionName = registeredOptionsWithParameterUniqueMap
                    .values()
                    .stream()
                    // search if an alias already exists for current argument
                    .filter(opt -> opt.aliasesSet.contains(nextValue))
                    .findFirst()
                    .map(o -> o.name)
                    .orElse(null);

            if (optionName == null) {
                throw new IllegalStateException("Error : Name of option is null");
            }
            var currentOption = registeredOptionsWithParameterUniqueMap.get(optionName);
            if (currentOption == null) {
                throw new IllegalStateException("Error: this option doesn't exist");
            }

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