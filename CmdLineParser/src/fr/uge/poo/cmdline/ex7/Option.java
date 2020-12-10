package fr.uge.poo.cmdline.ex7;

import java.util.*;
import java.util.function.Consumer;

public class Option {

    private final boolean isMandatory;
    private final int numberArguments;
    private final String name;
    private final Set<String> aliasesSet;
    private final Consumer<List<String>> acListConsumer;
    private final String documentation;
    private final List<String> conflicts;

    /**
     * Ce contrcuteur est privé pour respecter le design pattern du Builder. Il ne faut surtout pas
     * le mettre directement visible car cela nous forcereai a ajouter un nouveau constructeur à chaque fois
     * que l'on ajoute une nouvelle option.
     * @param builder objet builder
     */
    private Option(OptionBuilder builder) {
        this.numberArguments = builder.numberArguments;
        this.name = builder.name;
        this.isMandatory = builder.isMandatory;
        this.aliasesSet = builder.aliasesSet;
        this.acListConsumer = builder.acListConsumer;
        this.documentation = builder.documentation;
        this.conflicts = builder.conflicts;
    }


    public boolean isMandatory() {
        return isMandatory;
    }

    public int getNumberArguments() {
        return numberArguments;
    }

    public String getName() {
        return name;
    }

    public Set<String> getAliasesSet() {
        return aliasesSet;
    }

    public Consumer<List<String>> getAcListConsumer() {
        return acListConsumer;
    }

    public String haveDocumentation() {
        return documentation;
    }

    public List<String> getConflicts() {
        return conflicts;
    }

    @Override
    public String toString() {
        return "Option{" +
                "name='" + name + '\'' +
                '}';
    }

    public static class OptionBuilder {

        private boolean isMandatory;
        private int numberArguments;
        private String name;
        private final Set<String> aliasesSet;
        private Consumer<List<String>> acListConsumer;
        private String documentation = "";
        private final List<String> conflicts;


        public OptionBuilder(String name, int numberArguments, Consumer<List<String>> acListConsumer) {
            this.numberArguments = numberArguments;
            this.name = name;
            this.acListConsumer = acListConsumer;
            this.aliasesSet = new HashSet<>();
            this.conflicts =  new ArrayList<>();
        }

        public OptionBuilder isMandatory() {
            this.isMandatory = true;
            return this;
        }

        public OptionBuilder setNumberArguments(int numberArguments) {
            this.numberArguments = numberArguments;
            return this;
        }

        public OptionBuilder setAcListConsumer(Consumer<List<String>> acListConsumer) {
            this.acListConsumer = acListConsumer;
            return this;
        }

        public OptionBuilder setName(String name) {
            this.name = name;
            return this;
        }

        public OptionBuilder addAlias(String alias) {
            Objects.requireNonNull(alias);
            aliasesSet.add(alias);
            return this;
        }

        public OptionBuilder setDocumentation(String documentation) {
            Objects.requireNonNull(documentation);
            this.documentation = documentation;
            return this;
        }

        public OptionBuilder conflictWith(String optionName) {
            Objects.requireNonNull(optionName);
            this.conflicts.add(optionName);
            return this;
        }

        public Option build() {
            if (this.numberArguments < 0 || this.name.isBlank() ||
                    this.name.isEmpty() || this.acListConsumer == null) {
                throw new IllegalStateException("Error when building Option");
            }
            return new Option(this);
        }
    }

}
