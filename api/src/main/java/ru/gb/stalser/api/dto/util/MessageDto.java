package ru.gb.stalser.api.dto.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@Schema(description = "Information or error message", name = "Message")
public class MessageDto {
    @Schema(description = "Information or error message")
    @JsonProperty("message")
    private String message;
    @Schema(description = "Message Date")
    @JsonProperty("date")
    private Date date;

    public MessageDto() {
        this.date = new Date();
    }

    public MessageDto(String message) {
        this.message = message;
        this.date = new Date();
    }
}
