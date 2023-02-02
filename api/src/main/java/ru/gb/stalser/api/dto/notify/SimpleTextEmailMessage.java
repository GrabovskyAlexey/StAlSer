package ru.gb.stalser.api.dto.notify;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

/**
 * Info to create simple text email message
 *
 * @author GrabovskyAlexey
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class SimpleTextEmailMessage extends EmailMessage{
    private String text;
}
