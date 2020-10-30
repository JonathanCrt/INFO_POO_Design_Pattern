package fr.uge.poo.ducks;

import java.util.ServiceLoader;

public class DuckFarmBetter {

    public static void main(String[] args) {
        ServiceLoader<DuckFactory> loaderDuckFactory = ServiceLoader.load(fr.uge.poo.ducks.DuckFactory.class);

        for (DuckFactory duckFactory : loaderDuckFactory) {
            System.out.println(duckFactory.withName("Fifi").quack());
            System.out.println(duckFactory.withName("Riri").quack());
            System.out.println(duckFactory.withName("Loulou").quack());
        }

        /******* Without factory **********/
        ServiceLoader<Duck> loaderDuck = ServiceLoader.load(fr.uge.poo.ducks.Duck.class);
        String[] namesOfDucks = {"Riri", "Fifi", "Loulou"};
        var i = 0;
        for (var duck : loaderDuck) {
            duck.setName(namesOfDucks[i]);
            i++;
            System.out.println(duck.quack());
        }
    }

}
