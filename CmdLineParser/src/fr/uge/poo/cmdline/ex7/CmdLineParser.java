package fr.uge.poo.cmdline.ex7;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CmdLineParser {

    /**
     * Interface fonctionelle -> on veut spécifier un traitement spécfique sur un algo spécifique
     */
    @FunctionalInterface
    interface ParameterRetrievalStrategy {
        List<String> treatParameters(int numberArguments, Iterator<String> iterator, Predicate<String> predicate);
    }

    /******************************  Observer  ******************************/

    interface OptionsObserver {
        void onRegisteredOption(OptionsManager optionsManager, Option option);

        void onProcessedOption(OptionsManager optionsManager, Option option);

        void onFinishedProcess(OptionsManager optionsManager);
    }

    /******************************  Observables  ******************************/

    static class LoggerObserver implements OptionsObserver {

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            System.out.println("Option " + option + " is registered");
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            System.out.println("Option " + option + " is processed");
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
            System.out.println("Process method is finished");
        }
    }

    static class MandatoryObserver implements OptionsObserver {

        private Set<String> mandatoryOptionsList = new HashSet<>();

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            // if and only if an option is mandatory, we add it to map
            if (option.isMandatory()) {
                this.mandatoryOptionsList.add(option.getName());
            }
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            // The option has been resgitered so we can delete it
            this.mandatoryOptionsList.remove(option.getName());
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
            // As all options was registered, we can delete all
            if (!mandatoryOptionsList.isEmpty()) {
                throw new ParseException("Error : All mandatory options are not processed");
            }
        }
    }

    static class DocumentationObserver implements OptionsObserver {

        private TreeMap<String, String> documentationOptionsTreeMap = new TreeMap<>(); // treemap have a sort

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            // we add option which only have documentation
            if (option.haveDocumentation() != null) {
                documentationOptionsTreeMap.put(option.getName(), option.haveDocumentation());
            }
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
        }

        public String usage() {
            return documentationOptionsTreeMap
                    .entrySet()
                    .stream()
                    .map(option -> option.getKey() + " : " + option.getValue())
                    .collect(Collectors.joining("\n"));
        }

    }

    static class ConflictObserver implements OptionsObserver {

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            var conflictsList = option.getConflicts();
            for (var currentConflict : conflictsList) {
                if (optionsManager.byName.containsKey(currentConflict)) {
                    throw new ParseException("Error : there's a conflict of name");
                }
            }
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) {
        }
    }

    private static class OptionsManager {

        private final HashMap<String, Option> byName = new HashMap<>();
        private final ArrayList<OptionsObserver> optionsObservers = new ArrayList<>();

        /**
         * Register the option with all its possible names
         *
         * @param option
         */
        void register(Option option) {
            checkIfArgumentsAreNull(option);
            registerAux(option.getName(), option);
            for (var alias : option.getAliasesSet()) {
                registerAux(alias, option);
            }
        }

        private void registerAux(String name, Option option) {
            checkIfArgumentsAreNull(name, option);
            if (byName.containsKey(name)) {
                throw new IllegalStateException("Option " + name + " is already registered.");
            }
            byName.put(name, option);
            for (var optionObserver : optionsObservers) {
                optionObserver.onRegisteredOption(this, option);
            }
        }

        private void registerObserver(OptionsObserver observer) {
            checkIfArgumentsAreNull(observer);
            this.optionsObservers.add(observer);
        }

        /**
         * This method is called to signal that an option is encountered during
         * a command line process
         *
         * @param optionName
         * @return the corresponding object option if it exists
         */

        Optional<Option> processOption(String optionName) {
            checkIfArgumentsAreNull(optionName);
            var option = byName.get(optionName);
            if (option == null) {
                return Optional.empty();
            }
            for (var optionObserver : optionsObservers) {
                optionObserver.onProcessedOption(this, option);
            }
            return Optional.of(option);
        }

        /**
         * This method is called to signal the method process of the CmdLineParser is finished
         * Observer are notified when we finished to process line command
         */
        void finishProcess() {
            for (var optionObserver : optionsObservers) {
                optionObserver.onFinishedProcess(this);
            }
        }

    }


    /******************************  CmdLineParser  ******************************/

    private static final Logger LOGGER = Logger.getLogger(CmdLineParser.class.getName());
    private final Set<String> processedOptionsSet = new HashSet<>();
    private final OptionsManager optionsManager = new OptionsManager(); // default constructor
    private final DocumentationObserver documentationObserver = new DocumentationObserver(); // default constructor
    public static final ParameterRetrievalStrategy STANDARD = (numberArguments, iterator, predicate) -> {
        var listArguments = new ArrayList<String>();
        for (var i = 0; i < numberArguments; i++) {
            var value = iterator.next();
            if (value.startsWith("-")) {
                throw new ParseException(value + " is not an argument ! (Maybe an Option)");
            }
            listArguments.add(value);
        }
        return listArguments;
    };

    public static final ParameterRetrievalStrategy RELAXED = (numberArguments, iterator, predicate) -> {
        var listArguments = new ArrayList<String>();
        for (var i = 0; i < numberArguments; i++) {
            var value = iterator.next();
            if (value.startsWith("-")) {
                continue;
            }
            listArguments.add(value);
        }
        return listArguments;
    };

    public static final ParameterRetrievalStrategy OLDSCHOOL = (numberArguments, iterator, predicate) -> {
        var listArguments = new ArrayList<String>();
        for (var i = 0; i < numberArguments; i++) {
            var value = iterator.next();
            listArguments.add(value);
        }
        return listArguments;
    };

    public static final ParameterRetrievalStrategy SMARTRELAXED = (numberArguments, iterator, predicate) -> {
        var listArguments = new ArrayList<String>();
        for (var i = 0; i < numberArguments; i++) {
            var value = iterator.next();
            if (predicate.test(value)) {
                continue;
            }
            listArguments.add(value);
        }
        return listArguments;
    };


    public CmdLineParser() {
        // register an observer
        //this.optionsManager.registerObserver(new LoggerObserver());
        this.optionsManager.registerObserver(new MandatoryObserver());
        this.optionsManager.registerObserver(documentationObserver);
        this.optionsManager.registerObserver(new ConflictObserver());
    }

    public void addFlag(String nameFlag, Runnable code) {
        var option = new Option.OptionBuilder(nameFlag, 0, o -> code.run()).build();
        this.optionsManager.register(option);
    }

    public void addOption(Option option) {
        checkIfArgumentsAreNull(option);
        this.optionsManager.register(option);
    }

    public void addOptionWithOneParameter(String optionName, Consumer<String> code) {
        checkIfArgumentsAreNull(optionName, code);
        var optionBuilder = new Option.OptionBuilder(optionName, 1, elt -> code.accept(elt.get(0)));
        var option = optionBuilder.build();
        this.optionsManager.register(option);

    }

    // to avoid code duplication
    public List<Path> process(String[] argumentsLineCommand) {
        return process(argumentsLineCommand, STANDARD);
    }

    public List<Path> process(String[] argumentsLineCommand, ParameterRetrievalStrategy strategy) {
        checkIfArgumentsAreNull((Object) argumentsLineCommand);
        var paths = new ArrayList<Path>();
        var iterator = Arrays.stream(argumentsLineCommand).iterator();

        while (iterator.hasNext()) {
            var nextValue = iterator.next(); // map key

            if (nextValue.startsWith("-")) { //  can be name file or argument of option

                var currentOption = this.optionsManager.processOption(nextValue)
                        .orElseThrow(() -> new ParseException("Error :" + nextValue + " unknown"));

                // get arguments of one option
                var optionsArguments = strategy.treatParameters(currentOption.getNumberArguments(), iterator, optionsManager.byName::containsKey);

                currentOption.getAcListConsumer().accept(optionsArguments);
                this.processedOptionsSet.add(currentOption.getName());
            } else {
                paths.add(Path.of(nextValue));
            }

        }
        this.optionsManager.finishProcess();
        return paths;
    }

    public void usage() {
        LOGGER.log(Level.INFO, "{}", this.documentationObserver.usage());
    }

    /******************************  Util  ******************************/
    private static void checkIfArgumentsAreNull(Object... args) {
        for (Object obj : args) {
            Objects.requireNonNull(obj);
        }
    }


}