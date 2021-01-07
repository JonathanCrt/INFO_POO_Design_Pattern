package fr.umlv.dp.exam;

import java.time.LocalDateTime;
import java.util.stream.Stream;

public interface IMessageHandler {
    LocalDateTime getDateMessage();
    Stream<Message> getLastMessages(int nMessages);

    //void postMessage(User sender, String content);
}
