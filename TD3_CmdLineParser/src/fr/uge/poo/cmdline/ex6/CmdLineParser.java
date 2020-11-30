package fr.uge.poo.cmdline.ex6;

import java.nio.file.Path;
import java.util.*;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CmdLineParser {

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
            if (option.isMandatory) {
                this.mandatoryOptionsList.add(option.name);
            }
        }

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            // The option has been resgitered so we can delete it
            this.mandatoryOptionsList.remove(option.name);
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

        private TreeMap<String, String> documentationOptionsTreeMap =  new TreeMap<>(); // treemap have a sort

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {
            // we add option which only have documentation
            if(option.haveDocumentation() != null) {
                documentationOptionsTreeMap.put(option.name, option.documentation);
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

    static class ConflitObserver implements OptionsObserver {

        @Override
        public void onRegisteredOption(OptionsManager optionsManager, Option option) {}

        @Override
        public void onProcessedOption(OptionsManager optionsManager, Option option) {
            var conflictsList =  option.getConflicts();
            for (var currentConflict : conflictsList) {
                if (optionsManager.byName.containsKey(currentConflict)) {
                    throw new ParseException("Error : there's a conflict of name");
                }
            }
        }

        @Override
        public void onFinishedProcess(OptionsManager optionsManager) { }
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
            registerAux(option.name, option);
            for (var alias : option.aliasesSet) {
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
    private final HashMap<String, Option> registeredOptionsMap = new HashMap<>();
    private final Set<String> processedOptionsSet = new HashSet<>();
    private final OptionsManager optionsManager = new OptionsManager(); // default constructor
    private final DocumentationObserver documentationObserver = new DocumentationObserver(); // default constructor

    public CmdLineParser() {
        // register an observer
        //this.optionsManager.registerObserver(new LoggerObserver());
        this.optionsManager.registerObserver(new MandatoryObserver());
        this.optionsManager.registerObserver(documentationObserver);
        this.optionsManager.registerObserver(new ConflitObserver());
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


    public List<Path> process(String[] argumentsLineCommand) {
        checkIfArgumentsAreNull((Object) argumentsLineCommand);
        var paths = new ArrayList<Path>();
        var iterator = Arrays.stream(argumentsLineCommand).iterator();

        while (iterator.hasNext()) {
            var nextValue = iterator.next(); // map key

            if (nextValue.startsWith("-")) { //  can be name file or argument of option

                var currentOption = this.optionsManager.processOption(nextValue)
                        .orElseThrow(() -> new ParseException("Error :" + nextValue + " unknown"));

                // get arguments of one option
                var optionsArguments = new ArrayList<String>();
                for (var i = 0; i < currentOption.numberArguments; i++) {
                    var value = iterator.next();
                    if (value.startsWith("-")) {
                        throw new ParseException(value + " is not an argument ! (Maybe an Option)");
                    }
                    optionsArguments.add(value);
                }
                currentOption.acListConsumer.accept(optionsArguments);
                this.processedOptionsSet.add(currentOption.name);
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