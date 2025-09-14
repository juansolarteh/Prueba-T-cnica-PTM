package ptm.pruebaTecnica.infrastructure.exceptionHandler;

import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ptm.pruebaTecnica.application.exceptions.ProductoNoExistenteException;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Manejo de errores de validación de argumentos en body
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<PropertyErrorDTO>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<PropertyErrorDTO> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(new PropertyErrorDTO(error.getField(), error.getDefaultMessage()))
        );
        
        return ResponseEntity.badRequest().body(errors);
    }

    // Manejo de errores de tipo incorrecto en parámetros de consulta o path variables
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<UniqueErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String message = ex.getRequiredType() != null ?
                String.format("El parámetro '%s' debe ser de tipo '%s'", ex.getName(), ex.getRequiredType().getSimpleName()) :
                String.format("El parámetro '%s' es inválido", ex.getName());
        UniqueErrorDTO response = new UniqueErrorDTO(message);
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de errores de validación de parámetros de consulta o path variables
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<UniqueErrorDTO> handleConstraintViolationException(ConstraintViolationException ex) {
        String message = ex.getConstraintViolations().stream()
                .map(violation -> String.format("El parámetro '%s' %s", violation.getPropertyPath(), violation.getMessage()))
                .reduce((msg1, msg2) -> msg1 + "; " + msg2)
                .orElse("Error de validación en los parámetros");
        UniqueErrorDTO response = new UniqueErrorDTO(message);
        return ResponseEntity.badRequest().body(response);
    }

    // Manejo de errores por parámetros faltantes en la solicitud
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<UniqueErrorDTO> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        String message = String.format("Falta el parámetro requerido: '%s'", ex.getParameterName());
        UniqueErrorDTO response = new UniqueErrorDTO(message);
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(ProductoNoExistenteException.class)
    public ResponseEntity<UniqueErrorDTO> handleProductoNoExistenteException(ProductoNoExistenteException ex) {
        UniqueErrorDTO response = new UniqueErrorDTO(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<UniqueErrorDTO> handleRuntimeException(RuntimeException ex) {
        logger.error(ex.getMessage(), ex);
        UniqueErrorDTO response = new UniqueErrorDTO("Error interno del servidor");
        return ResponseEntity.internalServerError().body(response);
    }
}
