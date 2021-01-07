package fr.umlv.dp.exam;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private final IMessageSender sender;
    private final String content;
    private final LocalDateTime localDateTime;

    private Message(IMessageSender sender, String content, LocalDateTime localDateTime) {
        this.sender = Objects.requireNonNull(sender);
        this.content = Objects.requireNonNull(content);
        this.localDateTime = localDateTime;
    }

    public static Message createMessage(IMessageSender sender, String content) {
        return new Message(sender, content, LocalDateTime.now());
    }

    /**
     * Returns the sender of the message.
     *
     * @return the sender of the message.
     */
    public IMessageSender getSender() {
        return sender;
    }

    /**
     * Returns the content of the message.
     *
     * @return the content of the message.
     */
    public String getContent() {
        return content;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    @Override
    public String toString() {
        return sender + ": " + content;
    }
}
