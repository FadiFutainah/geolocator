package geolocator.application.geolocator.exception;

import geolocator.application.geolocator.dto.ResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }
        return ResponseDto.exceptionResponse(HttpStatus.BAD_REQUEST, errors.toArray(String[]::new));
    }

    @ExceptionHandler(CommonExceptions.ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFound(CommonExceptions.ResourceNotFoundException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.NOT_FOUND, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CommonExceptions.BadRequestException.class)
    public ResponseEntity<Object> handleBadRequest(CommonExceptions.BadRequestException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CommonExceptions.UnauthorizedException.class)
    public ResponseEntity<Object> handleUnauthorized(CommonExceptions.UnauthorizedException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.UNAUTHORIZED, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CommonExceptions.InternalServerException.class)
    public ResponseEntity<Object> handleInternalServerError(CommonExceptions.InternalServerException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAll(Exception ex) {
        return ResponseDto.exceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CommonExceptions.ResourceAlreadyExistException.class)
    public ResponseEntity<Object> handleResourceAlreadyExist(CommonExceptions.ResourceAlreadyExistException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage());
    }

    @ExceptionHandler(CommonExceptions.BadCredentialsException.class)
    public ResponseEntity<Object> handleBadCredentials(CommonExceptions.BadCredentialsException ex) {
        return ResponseDto.exceptionResponse(HttpStatus.FORBIDDEN, ex.getLocalizedMessage());
    }
}
