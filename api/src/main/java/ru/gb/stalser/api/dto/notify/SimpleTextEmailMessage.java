package ru.gb.stalser.api.dto.notify;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * Info to create simple text email message
 *
 * @author GrabovskyAlexey
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SimpleTextEmailMessage extends EmailMessage{
    private String text;
}
