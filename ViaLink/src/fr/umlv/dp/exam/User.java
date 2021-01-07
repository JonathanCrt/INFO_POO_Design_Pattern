package fr.umlv.dp.exam;

import java.util.List;
import java.util.Objects;


/**
 * A user
 */
public class User extends AbstractMessageHandler implements IMessageSender {
    private final String firstName;
    private final String lastName;

    /**
     * Creates a user with a first name and a last name.
     *
     * @param firstName first name of the user.
     * @param lastName  last name of the user.
     */
    public User(String firstName, String lastName) {
        this.firstName = Objects.requireNonNull(firstName);
        this.lastName = Objects.requireNonNull(lastName);
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() ^ Integer.rotateLeft(lastName.hashCode(), 16);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return firstName.equals(user.firstName) &&
                lastName.equals(user.lastName);
    }

    /**
     * Returns the first name of the user.
     *
     * @return the first name of the user.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     *
     * @return the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * A list of all messages posted by the user, the last message is at the end of the list.
     *
     * @return a list of all messages.
     */
    public List<Message> messages() {
        return List.copyOf(messageDeque);
    }

    @Override
    public String toString() {
        return firstName + ' ' + lastName;
    }


    public void postMessage(IMessageSender sender, String content) {
        Objects.requireNonNull(sender);
        Objects.requireNonNull(content);
        var message = Message.createMessage(sender, content);
        messageDeque.add(message);
        messageManager.observersList.forEach(o -> o.onNotifyNewPostedMessage(message));
    }

    public void postMessageInGroup(IMessageSender sender, String content, Group group) {
        Objects.requireNonNull(sender);
        Objects.requireNonNull(content);
        var message = Message.createMessage(sender, content);
        group.postMessageInGroup(sender, content);
        messageManager.observersList.forEach(o -> o.onNotifyNewPostMessageInGroup(message));
    }

    public void registerObserver(IMessageObserver observer) {
        messageManager.register(observer);
    }

    @Override
    public void accept(IVisitor visitor, String msg) {
        visitor.visit(this, msg);
    }

    @Override
    public void accept(IVisitor visitor, String msg, Group group) {
        visitor.visit(this, msg, group);
    }
}
