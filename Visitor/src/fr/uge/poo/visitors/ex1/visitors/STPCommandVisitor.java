package fr.uge.poo.visitors.ex1.visitors;

import fr.uge.poo.visitors.ex1.ElapsedTimeCmd;
import fr.uge.poo.visitors.ex1.HelloCmd;
import fr.uge.poo.visitors.ex1.StartTimerCmd;
import fr.uge.poo.visitors.ex1.StopTimerCmd;

public interface STPCommandVisitor {
    void visit(HelloCmd helloCmd);

    void visit(StartTimerCmd startTimerCmd);

    void visit(StopTimerCmd stopTimerCmd);

    void visit(ElapsedTimeCmd elapsedTimeCmd);
}
