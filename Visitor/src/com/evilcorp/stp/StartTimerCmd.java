package com.evilcorp.stp;

import com.evilcorp.stp.STPCommand;

public class StartTimerCmd implements STPCommand {

    private int  timerId;

    public StartTimerCmd(int timerId){
        this.timerId=timerId;
    }

    public int getTimerId() {
        return timerId;
    }
}
