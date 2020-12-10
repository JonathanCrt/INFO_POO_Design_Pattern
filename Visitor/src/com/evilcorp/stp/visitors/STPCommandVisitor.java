package com.evilcorp.stp.visitors;

import com.evilcorp.stp.*;

public interface STPCommandVisitor {
    void visit(HelloCmd helloCmd);

    void visit(StartTimerCmd startTimerCmd);

    void visit(StopTimerCmd stopTimerCmd);

    void visit(ElapsedTimeCmd elapsedTimeCmd);

    void visit(QuickCmd chronoCommand);
}