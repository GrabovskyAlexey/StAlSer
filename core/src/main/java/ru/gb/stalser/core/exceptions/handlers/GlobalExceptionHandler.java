package ru.gb.stalser.core.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.gb.stalser.api.dto.util.MessageDto;
import ru.gb.stalser.api.dto.util.ValidationError;
import ru.gb.stalser.api.dto.util.ValidationErrorResponseDto;
import ru.gb.stalser.core.exceptions.*;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(InviteWithoutBoardException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchPasswordException(PasswordNotConfirmedException e){
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResourceNotFoundException(UserRoleNotFoundException e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({
            InviteWasExpiredException.class,
            DifferentEmailException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDto catchInviteException(RuntimeException e) {
        return new MessageDto(e.getMessage());
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDto onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        List<ValidationError> errors = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new ValidationError(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponseDto(errors);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationErrorResponseDto onConstraintValidationException(ConstraintViolationException e) {
        final List<ValidationError> violations = e.getConstraintViolations().stream()
                .map(error -> new ValidationError(error.getPropertyPath().toString(), error.getMessage()))
                .collect(Collectors.toList());
        return new ValidationErrorResponseDto(violations);
    }

    @ExceptionHandler
    public ResponseEntity<MessageDto> catchResetPasswordTokenExeption(ResetPasswordTokenExeption e) {
        return new ResponseEntity<>(new MessageDto(e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
