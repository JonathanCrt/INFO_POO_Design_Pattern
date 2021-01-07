package fr.umlv.dp.exam.visitor;

import fr.umlv.dp.exam.Group;
import fr.umlv.dp.exam.Robot;
import fr.umlv.dp.exam.User;

public class PostMessageVisitorImpl implements IVisitor {
    @Override
    public void visit(User user, String message) {
        user.postMessage(user, message);
    }

    @Override
    public void visit(Robot robot, String message, Group group) {
        robot.post(robot, message, group);
    }

    @Override
    public void visit(User user, String message, Group group) {
        user.postMessageInGroup(user, message, group);
    }
}
