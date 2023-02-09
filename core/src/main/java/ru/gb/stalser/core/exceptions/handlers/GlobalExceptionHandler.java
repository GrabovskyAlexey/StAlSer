package ru.gb.stalser.core.exceptions.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.exceptions.DifferentEmailException;
import ru.gb.stalser.core.exceptions.InviteWasExpiredException;
import ru.gb.stalser.core.exceptions.InviteWithoutBoardException;
import ru.gb.stalser.core.exceptions.UserRoleNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(InviteWithoutBoardException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(UserRoleNotFoundException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(InviteWasExpiredException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(DifferentEmailException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
