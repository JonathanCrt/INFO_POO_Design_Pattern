package fr.umlv.dp.exam;


import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class VialinkTest {

    @Test
    void addUserAndMessageToGroup() {
        var user = new User("Jonathan", "CRETE");
        var user1 = new User("Jonathan", "CRETE");
        var user2 = new User("Mélissa", "DA COSTA");

        var group = new Group();
        group.addUserToGroup(user);
        group.addUserToGroup(user1);
        group.addUserToGroup(user2);

        var msg  = Message.createMessage(user, "Salut");
        //group.postMessage(msg.getSender(), msg.getContent());
        assertEquals("Salut", group.getMessages().get(0).getContent());
        assertEquals(2, group.getUsers().size());
        assertEquals("Jonathan", group.getUsers().stream().findFirst().get().getFirstName());

    }

    @Test
    public void testGroupObserver() {
        var u1 = new User("Toto", "Rina");
        var u2 = new User("Titi", "Rina");
        var g1 = new Group();


        class GroupObserverImpl implements IMessageObserver {
            @Override
            public void onNotifyNewPostedMessage(Message message) {

            }

            @Override
            public void onNotifyNewPostMessageInGroup(Message message) {
                System.out.println("message posted for group");
            }
        }

        g1.addUserToGroup(u1);

        u1.registerObserver(new GroupObserverImpl());
        u1.postMessage(u1, "toto");
        u1.postMessageInGroup(u1, "toto", g1);
    }
}