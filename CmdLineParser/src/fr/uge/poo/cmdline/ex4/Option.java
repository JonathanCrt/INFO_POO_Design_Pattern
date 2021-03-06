package fr.uge.poo.cmdline.ex4;

import java.util.List;
import java.util.function.Consumer;

public class Option {

    boolean isMandatory;
    final int numberArguments;
    final String name;
    final Consumer<List<String>> acListConsumer;

    /**
     * Ce contrcuteur est privé pour respecter le design pattern du Builder. Il ne faut surtout pas
     * le mettre directement visible car cela nous forcereai a ajouter un nouveau constructeur à chaque fois
     * que l'on ajoute une nouvelle option.
     * @param builder objet builder
     */
    private Option(OptionBuilder builder) {
        this.numberArguments = builder.numberArguments;
        this.name = builder.name;
        this.acListConsumer = builder.acListConsumer;
    }

    public static class OptionBuilder {

        boolean isMandatory;
        int numberArguments;
        String name;
        Consumer<List<String>> acListConsumer;


        public OptionBuilder(String name, int numberArguments, Consumer<List<String>> acListConsumer) {
            this.numberArguments = numberArguments;
            this.name = name;
            this.acListConsumer = acListConsumer;
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

        public Option build() {
            if (this.numberArguments < 0 || this.name.isBlank() ||
                    this.name.isEmpty() || this.acListConsumer == null) {
                throw new IllegalStateException("Error when building Option");
            }
            return new Option(this);
        }
    }

}
