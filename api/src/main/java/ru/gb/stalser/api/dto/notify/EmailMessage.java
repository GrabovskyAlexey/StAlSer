package ru.gb.stalser.api.dto.notify;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class EmailMessage {
    private String from;
    private String senderDisplayName;
    private String to;
    private String receiverDisplayName;
    private String subject;
}