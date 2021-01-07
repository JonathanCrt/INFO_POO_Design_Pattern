package fr.umlv.dp.exam;

public interface IVisitor {
    void visit(User user, String message);
    void visit(Robot robot, String message, Group group);
    void visit(User user, String message, Group group);
}
