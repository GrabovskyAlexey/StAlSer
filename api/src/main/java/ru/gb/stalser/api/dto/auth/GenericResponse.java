package ru.gb.stalser.api.dto.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Schema(description = "Ответ сброса пароля", name = "GenericResponse")
@NoArgsConstructor
@AllArgsConstructor
public class GenericResponse {

    private String message;
    private String error;

    public GenericResponse(final String message) {
        super();
        this.message = message;
    }


}
