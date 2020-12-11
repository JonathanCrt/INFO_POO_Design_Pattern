package fr.uge.poo.visitors.ex1;

import fr.uge.poo.visitors.ex1.visitors.STPCommandVisitor;

public class StopTimerCmd implements STPCommand {

    private int timerId;

    public StopTimerCmd(int timerId) {
        this.timerId = timerId;
    }

    public int getTimerId() {
        return timerId;
    }

    @Override
    public void accept(STPCommandVisitor visitor) {
        visitor.visit(this);
    }
}
