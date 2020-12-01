package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.STPParser;

import java.util.Scanner;

public class TrivialPolymorph {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            var line = scanner.nextLine();
            if (line.equals("quit")) {
                break;
            }

            var answer = STPParser.parse(line);

            if (!answer.isPresent()) {
                System.out.println("Pas compris");
                continue;
            }
            var stpCommand = answer.get();
            // stpCommand.perform();
        }

    }
}
