package ru.gb.stalser.notification.services.interfaces;


import ru.gb.stalser.api.dto.notify.SimpleTextEmailMessage;

public interface NotificationService {
    /**
     * Send simple text mail to user
     *
     * @param context Simple text message info for send
     */
    void sendSimpleTextEmail(SimpleTextEmailMessage context);
}
