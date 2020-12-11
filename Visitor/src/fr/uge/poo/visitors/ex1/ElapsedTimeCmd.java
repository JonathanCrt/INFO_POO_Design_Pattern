package fr.uge.poo.visitors.ex1;

import fr.uge.poo.visitors.ex1.visitors.STPCommandVisitor;

import java.util.List;
import java.util.Objects;

public class ElapsedTimeCmd implements STPCommand {

    private final List<Integer> timers;

    public ElapsedTimeCmd(List<Integer> timers) {
        Objects.requireNonNull(timers);
        this.timers = List.copyOf(timers);
    }

    public List<Integer> getTimers() {
        return timers;
    }

    @Override
    public void accept(STPCommandVisitor visitor) {
        visitor.visit(this);
    }
}
