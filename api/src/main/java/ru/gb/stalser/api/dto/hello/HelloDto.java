package ru.gb.stalser.api.dto.hello;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.gb.stalser.api.dto.util.MessageDto;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Hello information", name = "Hello")
public class HelloDto {
    @Schema(description = "Hello id", example = "null")
    @Min(value = 1)
    private Long id;

    @NotEmpty(message = "Название hello не может быть пустым")
    @Size(min = 3, max = 50, message = "Название hello должно содержать от 3  до 50 символов")
    @Schema(description = "Hello name")
    @JsonProperty("helloName")
    private String helloName;

    @Schema(description = "Messages in hello")
    @JsonProperty("messages")
    private List<MessageDto> messages;

    @Schema(description = "Sender")
    @JsonProperty("helloSender")
    @Size(min = 5, message = "Описание продукта должно содержать от минимум 5 символов")
    private String helloSender;

    @Schema(description = "Hello rating", example = "3.2")
    @Min(0)
    @Max(5)
    @JsonProperty("rating")
    private BigDecimal rating;

    @Schema(description = "Порядковый номер", example = "47")
    @NotEmpty(message = "Порядковый номер не может быть пустой")
    @Positive
    @JsonProperty("helloNumber")
    private Integer helloNumber;

}
