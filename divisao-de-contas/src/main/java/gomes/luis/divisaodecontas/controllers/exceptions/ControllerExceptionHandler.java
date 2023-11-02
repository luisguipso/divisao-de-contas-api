package gomes.luis.divisaodecontas.controllers.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroPersonalizado> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        HttpStatus noContent = HttpStatus.NO_CONTENT;
        ErroPersonalizado erro = new ErroPersonalizado()
                .setTimestamp(Instant.now())
                .setStatus(noContent.value())
                .setError(noContent.getReasonPhrase())
                .setMessage(e.getMessage())
                .setPath(request.getRequestURI());
        return new ResponseEntity<>(erro, noContent);
    }

    @ExceptionHandler(AuthenticationCredentialsNotFoundException.class)
    public ResponseEntity<ErroPersonalizado> authenticationCredentialsNotFound(AuthenticationCredentialsNotFoundException e, HttpServletRequest request){
        HttpStatus unauthorized = HttpStatus.UNAUTHORIZED;
        ErroPersonalizado erro = new ErroPersonalizado()
                .setTimestamp(Instant.now())
                .setStatus(unauthorized.value())
                .setError(unauthorized.getReasonPhrase())
                .setMessage(e.getMessage())
                .setPath(request.getRequestURI());
        return new ResponseEntity<>(erro, unauthorized);
    }
}
