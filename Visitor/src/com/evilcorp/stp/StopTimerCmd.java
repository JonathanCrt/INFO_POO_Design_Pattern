package com.evilcorp.stp;

import com.evilcorp.stp.visitors.STPCommandVisitor;

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
