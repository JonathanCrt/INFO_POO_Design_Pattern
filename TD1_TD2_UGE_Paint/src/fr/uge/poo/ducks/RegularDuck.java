package fr.uge.poo.ducks;

import java.util.Objects;

public class RegularDuck implements Duck {

    private String name;

    public RegularDuck() {
        this("Anonymous");
    }

    public RegularDuck(String name) {
        this.name = name;
    }

    @Override
    public String quack() {
        return "RegularDuck[" + name + "] says quack.";
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

}