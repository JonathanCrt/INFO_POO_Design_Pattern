package com.evilcorp.stp.visitors;

import com.evilcorp.stp.*;

import java.time.LocalDateTime;
import java.util.*;

public class STPCommandVisitorImpl implements STPCommandVisitor {

    private final CommandManager commandManager;

    public STPCommandVisitorImpl() {
        this.commandManager = new CommandManager();
        this.commandManager.register(new NumberByTypeCommandObserver());
        this.commandManager.register(new AverageTimeChronoObserver());
    }

    public interface CommandObserver {
        void onUsageCommand(STPCommand command, CommandManager commandManager);

        void onFinish(); // to observe end process
    }

    public static class NumberByTypeCommandObserver implements CommandObserver {

        private final Map<Class<? extends STPCommand>, Integer> counterTypeCommandMap = new HashMap<>();

        @Override
        public void onUsageCommand(STPCommand command, CommandManager commandManager) {
            Objects.requireNonNull(command);
            counterTypeCommandMap.compute(command.getClass(), (key, value) -> (value == null) ? 1 : value++);
        }

        @Override
        public void onFinish() {
            /*
            for(var set : counterTypeCommandMap.entrySet()) {
                System.out.println(set.getKey() + "" + set.getValue());
            }
             */
            counterTypeCommandMap.forEach((key, value) -> System.out.println(key + " " + value));
        }
    }

    public static class AverageTimeChronoObserver implements CommandObserver {

        private final ArrayList<Long> elapsedTimes =  new ArrayList<>();

        @Override
        public void onUsageCommand(STPCommand command, CommandManager commandManager) {
            Objects.requireNonNull(command);
            var timerId = ((StopTimerCmd)command).getTimerId();
            var currentTime = System.currentTimeMillis();
            var startTime = commandManager.timers.get(timerId);
            var calculatedTime = currentTime - startTime;
            elapsedTimes.add(calculatedTime);
        }

        @Override
        public void onFinish() {
            var optAverageTime = elapsedTimes.stream()
                    .mapToLong(s -> s).average();
            if(optAverageTime.isPresent()) {
                System.out.println("Average Time : " + optAverageTime.getAsDouble());
            }

        }
    }

    public class CommandManager {

        private final Map<Integer, Long> timers = new HashMap<>();

        private final ArrayList<CommandObserver> observersList = new ArrayList<>();

        public void register(CommandObserver observer) {
            Objects.requireNonNull(observer);
            observersList.add(observer);
        }

        public void unregister(CommandObserver observer) {
            Objects.requireNonNull(observer);
            if (!observersList.remove(observer)) {
                throw new IllegalStateException();
            }
        }


    }

    @Override
    public void visit(HelloCmd helloCmd) {
        System.out.println("Hello the current date is " + LocalDateTime.now());
    }

    @Override
    public void visit(StartTimerCmd cmd) {
        var timerId = cmd.getTimerId();
        if (commandManager.timers.get(timerId) != null) {
            System.out.println("Timer " + timerId + " was already started");
        }
        var currentTime = System.currentTimeMillis();
        commandManager.timers.put(timerId, currentTime);
        System.out.println("Timer " + timerId + " started");
    }

    @Override
    public void visit(StopTimerCmd cmd) {
        var timerId = cmd.getTimerId();
        var startTime = commandManager.timers.get(timerId);
        if (startTime == null) {
            System.out.println("Timer " + timerId + " was never started");
        }
        var currentTime = System.currentTimeMillis();
        System.out.println("Timer " + timerId + " was stopped after running for " + (currentTime - startTime) + "ms");
        commandManager.timers.put(timerId, null);
    }

    @Override
    public void visit(ElapsedTimeCmd cmd) {
        var currentTime = System.currentTimeMillis();
        for (var timerId : cmd.getTimers()) {
            var startTime = commandManager.timers.get(timerId);
            if (startTime == null) {
                System.out.println("Unknown timer " + timerId);
                continue;
            }
            System.out.println("Ellapsed time on timerId " + timerId + " : " + (currentTime - startTime) + "ms");
        }
    }
}
