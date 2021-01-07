package fr.umlv.dp.exam;

import java.util.*;

public class Group extends AbstractMessageHandler {
    private final Set<User> users = new LinkedHashSet<>();

    public void addUserToGroup(User user) {
        Objects.requireNonNull(user);
        users.add(user);
    }

    public void displayContent() {
        users.forEach(elt -> System.out.println(elt.getFirstName() + " " + elt.getLastName()));
    }

    public Set<User> getUsers() {
        return users;
    }

    public Boolean getUser(User user) {
        return users.contains(user);
    }

    public List<Message> getMessages() {
        return List.copyOf(messageDeque);
    }

    public void postMessageInGroup(IMessageSender sender, String content) {
        Objects.requireNonNull(sender);
        Objects.requireNonNull(content);
        if (!users.contains(sender)) {
            throw new IllegalStateException("User not in this group");
        }
        messageDeque.add(Message.createMessage(sender, content));
    }


    public static void main(String[] args) {
        var user = new User("Jonathan", "CRETE");
        var user1 = new User("Jonathan", "CRETE");
        var user2 = new User("Mélissa", "DA COSTA");
        var user3 = new User("Judi", "KOFFI");

        user.postMessage(user, "Salut !");

        var group = new Group();

        group.addUserToGroup(user3);
        group.addUserToGroup(user);
        group.addUserToGroup(user1);
        group.addUserToGroup(user2);

        group.displayContent();

    }
}
