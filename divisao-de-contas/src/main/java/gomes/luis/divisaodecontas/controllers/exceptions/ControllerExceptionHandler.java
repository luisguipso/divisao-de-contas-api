package gomes.luis.divisaodecontas.controllers.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.time.Instant;



@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErroPersonalizado> entityNotFound(EntityNotFoundException e, HttpServletRequest request){
        ErroPersonalizado erro = new ErroPersonalizado();
        erro.setTimestamp(Instant.now());
        erro.setStatus(HttpStatus.NO_CONTENT.value());
        erro.setError("Recurso não encontrado.");
        erro.setMessage(e.getMessage());
        erro.setPath(request.getRequestURI());
        return new ResponseEntity<ErroPersonalizado>(erro, HttpStatus.NO_CONTENT);
    }
}
