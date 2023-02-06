package ru.gb.stalser.notification.services;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.MailParseException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.gb.stalser.api.dto.notify.SimpleTextEmailMessage;
import ru.gb.stalser.notification.services.interfaces.NotificationService;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final JavaMailSender mailSender;

    @Override
    @KafkaListener(topics = "simple-text-email", groupId = "notify")
    public void sendSimpleTextEmail(final SimpleTextEmailMessage context) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(context.getTo());
            message.setFrom(context.getFrom());
            message.setSubject(context.getSubject());
            message.setText(context.getText());
            mailSender.send(message);
        } catch (MailParseException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
