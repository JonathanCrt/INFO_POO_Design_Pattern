package fr.uge.poo.cmdline.ex5;

import java.util.*;
import java.util.function.Consumer;

public class Option {

    boolean isMandatory;
    final int numberArguments;
    final String name;

    final Set<String> aliasesSet;

    final Consumer<List<String>> acListConsumer;

    final String documentation;

    /**
     * Ce contrcuteur est privé pour respecter le design pattern du Builder. Il ne faut surtout pas
     * le mettre directement visible car cela nous forcereai a ajouter un nouveau constructeur à chaque fois
     * que l'on ajoute une nouvelle option.
     * @param builder objet builder
     */
    private Option(OptionBuilder builder) {
        this.numberArguments = builder.numberArguments;
        this.name = builder.name;
        this.aliasesSet = new HashSet<>();
        this.acListConsumer = builder.acListConsumer;
        this.documentation = builder.documentation;
    }

    public static class OptionBuilder {

        boolean isMandatory;
        int numberArguments;
        String name;
        Set<String> aliasesSet;

        Consumer<List<String>> acListConsumer;

        String documentation = "";

        public OptionBuilder(String name, int numberArguments, Consumer<List<String>> acListConsumer) {
            this.numberArguments = numberArguments;
            this.name = name;
            this.acListConsumer = acListConsumer;
            this.aliasesSet = new HashSet<>();
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

        public Option build() {
            if (this.numberArguments < 0 || this.name.isBlank() ||
                    this.name.isEmpty() || this.acListConsumer == null) {
                throw new IllegalStateException("Error when building Option");
            }
            return new Option(this);
        }
    }

}
