package fr.uge.poo.ducks;

import java.util.Objects;

public class RubberDuck implements Duck {

    private String name;

    public RubberDuck(){
        this("Anonymous");
    }

    public RubberDuck(String name){
        this.name=name;
    }

    @Override
    public String quack() {
        return "RubberDuck["+name+"] refuses to quack.";
    }

    @Override
    public void setName(String name) {
        Objects.requireNonNull(name);
        this.name = name;
    }

}
