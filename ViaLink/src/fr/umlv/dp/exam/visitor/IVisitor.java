package fr.umlv.dp.exam.visitor;

import fr.umlv.dp.exam.Group;
import fr.umlv.dp.exam.Robot;
import fr.umlv.dp.exam.User;

public interface IVisitor {
    void visit(User user, String message);
    void visit(Robot robot, String message, Group group);
    void visit(User user, String message, Group group);
}
