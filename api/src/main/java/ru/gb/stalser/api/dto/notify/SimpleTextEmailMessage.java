package ru.gb.stalser.api.dto.notify;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Info to create simple text email message
 *
 * @author GrabovskyAlexey
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class SimpleTextEmailMessage extends EmailMessage{
    private String text;
}
