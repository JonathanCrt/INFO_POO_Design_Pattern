package fr.umlv.dp.exam;

public class Robot implements IMessageSender {
    private final User robot;

    public Robot(String firstname, String lastName) {
        this.robot = new User(firstname, lastName);
    }

    @Override
    public String toString() {
        return "Robot{" +
                "robot=" + robot +
                '}';
    }

    @Override
    public void accept(IVisitor visitor, String msg) {
        throw new AssertionError();
    }

    @Override
    public void accept(IVisitor visitor, String msg, Group group) {
        visitor.visit(this, msg, group);
    }

    public void post(Robot sender, String message, Group group) {
        robot.postMessageInGroup(sender, message, group);
    }
}
