package fr.umlv.dp.exam;

public interface IMessageSender {
    void accept(IVisitor visitor, String msg);
    void accept(IVisitor visitor, String msg, Group group);
}
