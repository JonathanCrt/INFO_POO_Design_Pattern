package fr.uge.poo.cmdline.ex5;

import fr.uge.poo.cmdline.ex4.ProcessException;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class CmdLineParser {

    private final HashMap<String, Option> registeredOptionsWithParameterUniqueMap = new HashMap<>();
    private final Set<String> mandatoryOptionsSet = new HashSet<>();
    private final Set<String> processedOptionsSet = new HashSet<>();
    private final HashMap<String, Set<String>> aliasOptionsMap = new HashMap<>();


    public void addFlag(String name, Runnable runnable) {
        checkIfArgumentsAreNull(name, runnable);
        var option = new Option.OptionBuilder(name, 0, strings -> runnable.run()).build();
        this.addOptionWrapper(option);
    }

    public void addOptionWithOneParameter(String name, Consumer<String> consumer) {
        checkIfArgumentsAreNull(name, consumer);
        var option = new Option.OptionBuilder(name, 1, strings -> consumer.accept(strings.get(0))).build();
        addOptionWrapper(option);
    }

    public void addOption(Option option) {
        this.addOptionWrapper(option);
    }

    private void addOptionWrapper(Option option) {
        if (!option.name.startsWith("-")) {
            throw new IllegalArgumentException(option.name + " must be started with '-'");
        }
        if (registeredOptionsWithParameterUniqueMap.containsKey(option.name)) {
            throw new IllegalStateException(option.name + " is already registered");
        }
        registeredOptionsWithParameterUniqueMap.put(option.name, option);
        aliasOptionsMap.put(option.name, option.aliasesSet);
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

            var optionName = this.findOptionName(nextValue)
                    .orElseThrow(() -> new IllegalStateException("this option doesn't exist " + nextValue));
            var currentOption = registeredOptionsWithParameterUniqueMap.get(optionName);

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

    private Optional<String> findOptionName(String value) {
        if (aliasOptionsMap.containsKey(value)) {
            return Optional.of(value);
        }
        return aliasOptionsMap.entrySet()
                .stream()
                .filter(e -> e.getValue().contains(value))
                .findFirst().map(Map.Entry::getKey);
    }


    private static void checkIfArgumentsAreNull(Object... args) {
        for (Object obj : args) {
            Objects.requireNonNull(obj);
        }
    }


}