package ru.gb.stalser.api.dto.notify;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class EmailMessage {
    private String from;
    private String senderDisplayName;
    private String to;
    private String receiverDisplayName;
    private String subject;
}