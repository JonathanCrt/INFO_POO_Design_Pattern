package fr.umlv.dp.exam;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;

class AbstractMessageHandler implements IMessageHandler {

    MessageManager messageManager = new MessageManager();
    final Deque<Message> messageDeque = new ArrayDeque<>();

    static class MessageManager {

        final List<IMessageObserver> observersList = new ArrayList<>();

        public void register(IMessageObserver observer) {
            Objects.requireNonNull(observer);
            observersList.add(observer);
        }

        public void unRegister(IMessageObserver observer) {
            Objects.requireNonNull(observer);
            if (!observersList.remove(observer)) {
                throw new IllegalStateException();
            }
        }

    }

    @Override
    public LocalDateTime getDateMessage() {
        return Optional.of(messageDeque.getLast().getLocalDateTime()).orElseThrow();
    }

    @Override
    public Stream<Message> getLastMessages(int nMessages) {
        if (nMessages < 0) {
            throw new IllegalArgumentException("Error :  need a positive number of last messages");
        }
        return messageDeque.stream().limit(nMessages);
    }




}
