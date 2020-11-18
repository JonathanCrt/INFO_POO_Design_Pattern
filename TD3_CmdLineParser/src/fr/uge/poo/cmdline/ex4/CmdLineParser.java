package fr.uge.poo.cmdline.ex4;

import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptionsWithParameterUniqueMap = new HashMap<>();

    private final Set<String> processedOptionsSet = new HashSet<>();


    public void registerOption(Option option) {
        checkIfArgumentsAreNull(option);
        if (registeredOptionsWithParameterUniqueMap.get(option.name) != null) {
            throw new IllegalStateException("Argument already exists");
        }
        registeredOptionsWithParameterUniqueMap.put(option.name, option);
    }


    public List<Path> process(String[] argumentsLineCommand) throws ProcessException {
        checkIfArgumentsAreNull(argumentsLineCommand);
        var paths = new ArrayList<Path>();
        var iterator = Arrays.stream(argumentsLineCommand).iterator();

        while (iterator.hasNext()) {
            var value = iterator.next(); // map key
            if (!value.startsWith("-")) { //  can be name file or argument of option
                paths.add(Path.of(value));
                continue;
            }

            var currentOption = registeredOptionsWithParameterUniqueMap.get(value);
            if (currentOption == null) {
                throw new IllegalStateException("Error this option doesn't exist");
            }

            // get arguments of one option
            var optionArguments = IntStream
                    .range(0, currentOption.numberArguments)
                    .mapToObj(arg -> iterator.next()) // mapToObj more specific
                    .collect(Collectors.toList());

            currentOption.acListConsumer.accept(optionArguments);
            this.processedOptionsSet.add(currentOption.name);
        }

        /*
           var mandatoryOptionsSet = new HashSet<String>();
           for(var option: registeredOptionsWithParameterUniqueMap.values()) {
               if(option.isMandatory) {
                   this.mandatoryOptionsSet.add(option.name);
               }
           }
        */

        // get all options which are mandatory, more elegant with stream
        var mandatoryOptionsSet = registeredOptionsWithParameterUniqueMap
                .values()
                .stream()
                .filter(option -> option.isMandatory)
                .map(option -> option.name)
                .collect(Collectors.toSet()); // Stream<Option> -> Stream<Option.Name>

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