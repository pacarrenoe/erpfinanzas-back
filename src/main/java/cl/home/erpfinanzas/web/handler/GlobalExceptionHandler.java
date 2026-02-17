package cl.home.erpfinanzas.web.handler;

import cl.home.erpfinanzas.web.response.ApiResponse;
import cl.home.erpfinanzas.web.response.ResponseFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<?>> handleRuntime(RuntimeException ex) {

        ApiResponse<?> response =
                ResponseFactory.error(HttpStatus.PRECONDITION_FAILED, ex.getMessage());

        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<?>> handleGeneric(Exception ex) {

        ApiResponse<?> response =
                ResponseFactory.error(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
