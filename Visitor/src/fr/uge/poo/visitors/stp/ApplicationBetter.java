package fr.uge.poo.visitors.stp;

import com.evilcorp.stp.*;
import com.evilcorp.stp.visitors.STPCommandVisitor;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ApplicationBetter {

    private static class ContextVisitor implements STPCommandVisitor {
        private final Map<Integer, Long> timers = new HashMap<>();

        @Override
        public void visit(HelloCmd helloCmd) {
            System.out.println("Hello the current date is " + LocalDateTime.now());
        }

        @Override
        public void visit(StartTimerCmd cmd) {
            var timerId = cmd.getTimerId();
            if (timers.get(timerId) != null) {
                System.out.println("Timer " + timerId + " was already started");
            }
            var currentTime = System.currentTimeMillis();
            timers.put(timerId, currentTime);
            System.out.println("Timer " + timerId + " started");
        }

        @Override
        public void visit(StopTimerCmd cmd) {
            var timerId = cmd.getTimerId();
            var startTime = timers.get(timerId);
            if (startTime == null) {
                System.out.println("Timer " + timerId + " was never started");
            }
            var currentTime = System.currentTimeMillis();
            System.out.println("Timer " + timerId + " was stopped after running for " + (currentTime - startTime) + "ms");
            timers.put(timerId, null);
        }

        @Override
        public void visit(ElapsedTimeCmd cmd) {
            var currentTime = System.currentTimeMillis();
            for (var timerId : cmd.getTimers()) {
                var startTime = timers.get(timerId);
                if (startTime == null) {
                    System.out.println("Unknown timer " + timerId);
                    continue;
                }
                System.out.println("Ellapsed time on timerId " + timerId + " : " + (currentTime - startTime) + "ms");
            }
        }

        @Override
        public void visit(QuickCmd chronoCommand) {

        }
    }


    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var contextVisitor = new ContextVisitor();
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
            var command  = answer.get();
            command.accept(contextVisitor);
        }
    }
}
