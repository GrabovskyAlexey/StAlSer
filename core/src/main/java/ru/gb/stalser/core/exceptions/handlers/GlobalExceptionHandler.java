package ru.gb.stalser.core.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.core.exceptions.EmailAlreadyExistsException;
import ru.gb.stalser.core.exceptions.InviteWithoutBoardException;
import ru.gb.stalser.core.exceptions.UserAlreadyExistsException;
import ru.gb.stalser.core.exceptions.UserRoleNotFoundException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(InviteWithoutBoardException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(UserRoleNotFoundException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            EmailAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto catchRegisterException(RuntimeException e){
        log.warn("Catch exception {}\n Message: {}", e.getClass().getSimpleName(), e.getMessage());
        return new MessageDto(e.getMessage());
    }
}
