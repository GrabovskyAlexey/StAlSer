package ru.gb.stalser.api.dto.notify;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailMessage {
    private String from;
    private String senderDisplayName;
    private String to;
    private String receiverDisplayName;
    private String subject;
}