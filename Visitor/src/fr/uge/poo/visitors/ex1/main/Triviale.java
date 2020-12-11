package fr.uge.poo.visitors.ex1.main;


import fr.uge.poo.visitors.ex1.HelloCmd;
import fr.uge.poo.visitors.ex1.STPParser;

import java.util.Scanner;

public class Triviale {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if (line.equals("quit")) {
                break;
            }

            var answer = STPParser.parse(line);

            if (answer.isEmpty()) {
                System.out.println("Pas compris");
                continue;
            }
            var stpCommand = answer.get();
            if (stpCommand instanceof HelloCmd) {
                System.out.println("Au revoir");
            } else {
                System.out.println("Non implémenté");
            }
        }

    }
}

/**
 * Open closed principle, car dès que lon voudra implémenter de nouvelles commandes il faudra changer la méthode parse() de STParser
 * On veut réagir différemment au type de commandes => Il faudrai aller dans le code source de la libriairie, ajouter la méthode react()
 * dans l'interface puis l'implémenter dans toutes les classes
 * ON NE PEUT PAS ! C'est une librairie ! il faudrai redéfinir react() dans toutes les classes !
 * <p>
 * 1 -> polymorphisme -> Le polymorphisme est le fait de substituer à l'exécution un appel de méthode par un autre en fonction de la classe du receveur
 * (Slide 25 cours 2, Cours Java 1ére année)
 * late binding -> liaison tardive au compilateur
 * Exemple : Avec List<L> list : le list.add() se comporte différemment (appeller le bon code) s'il s'agit d'une ArrayList ou d'une Linkedlist() par exemple.
 * <p>
 * 2 -> Il nous faut implémenter un pattern visitor, afin d'ajouter des nouvelles opérations aux classes
 * implémentant l'interface STPCommand sans modifier ces classes
 * <p>
 * 3 -> Implémenter le pattern Observer, on va avoir une list des Observers.
 */