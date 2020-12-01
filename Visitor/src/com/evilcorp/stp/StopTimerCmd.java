package com.evilcorp.stp;

import com.evilcorp.stp.STPCommand;

public class StopTimerCmd implements STPCommand {

    private int  timerId;

    public StopTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }
}
