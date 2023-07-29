package gomes.luis.divisaodecontas.controllers.exceptions;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroPersonalizado> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        ErroPersonalizado erro = new ErroPersonalizado()
                .setTimestamp(Instant.now())
                .setStatus(HttpStatus.NO_CONTENT.value())
                .setError("Recurso não encontrado.")
                .setMessage(e.getMessage())
                .setPath(request.getRequestURI());
        return new ResponseEntity<ErroPersonalizado>(erro, HttpStatus.NO_CONTENT);
    }

}
