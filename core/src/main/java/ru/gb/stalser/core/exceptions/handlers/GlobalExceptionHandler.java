package ru.gb.stalser.core.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.exceptions.InviteWithoutBoardException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> catchResourceNotFoundException(InviteWithoutBoardException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.CONFLICT);
    }
}
