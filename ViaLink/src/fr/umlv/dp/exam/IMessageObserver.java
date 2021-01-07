package fr.umlv.dp.exam;

public interface IMessageObserver {
    void onNotifyNewPostedMessage(Message message);
    void onNotifyNewPostMessageInGroup(Message message);
}
