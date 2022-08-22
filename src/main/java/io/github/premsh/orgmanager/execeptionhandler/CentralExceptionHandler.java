package io.github.premsh.orgmanager.execeptionhandler;

import io.github.premsh.orgmanager.dto.response.ErrorDto;
import io.github.premsh.orgmanager.dto.response.ValidationErrorDto;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityAlreadyExistException;
import io.github.premsh.orgmanager.execeptionhandler.exceptions.EntityNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ControllerAdvice
public class CentralExceptionHandler extends ResponseEntityExceptionHandler {

    static final String METHOD_NOT_SUPPORTED = "No such method available in request path";
    static final String REQUEST_HANDLER_NOT_FOUND = "No handlers defined for request path";
    static final String BAD_REQUEST = "Bad request, please check format and syntax of request";
    static final String MEDIA_TYPE_NOT_SUPPORTED = "The request media type not supported by application";
    static final String MEDIA_TYPE_NOT_ACCEPTABLE = "The request media type not acceptable by application";
    static final String MISSING_PATH_VARIABLE = "Missing path variable error";
    static final String TYPE_MISMATCH = "Type mismatch occurred";
    static final String MESSAGE_NOT_READABLE = "Message not readable error";
    static final String MESSAGE_NOT_WRITABLE = "Message not writable error";
    static final String VALIDATION_ERROR = "Validation error occurred";

    //Overridden Common Inbuilt EXCEPTION HANDLERS
    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, METHOD_NOT_SUPPORTED, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, REQUEST_HANDLER_NOT_FOUND, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, BAD_REQUEST, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, MEDIA_TYPE_NOT_SUPPORTED, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotAcceptable(HttpMediaTypeNotAcceptableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, MEDIA_TYPE_NOT_ACCEPTABLE, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingPathVariable(MissingPathVariableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, MISSING_PATH_VARIABLE, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, TYPE_MISMATCH, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, MESSAGE_NOT_READABLE, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotWritable(HttpMessageNotWritableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, MESSAGE_NOT_WRITABLE, ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        return new ResponseEntity<>(new ErrorDto(status, "INTERNAL_SERVER_ERROR", ex), status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorDto response = new ErrorDto(status, VALIDATION_ERROR, ex);
        Set<ValidationErrorDto> verrors = new HashSet<>();
        for (FieldError verr:ex.getFieldErrors()) {
            ValidationErrorDto newVerror = new ValidationErrorDto(verr.getField(), (String) verr.getRejectedValue() ,verr.getDefaultMessage() );
            verrors.add(newVerror);
        }
        response.setValidationErrors(verrors.toArray(new ValidationErrorDto[0]));
        return new ResponseEntity<>(response, status);
    }



    //Custom EXCEPTION HANDLERS

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleEntityNotFound(EntityNotFoundException exep){
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND, exep.getMessage(), exep),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    protected ResponseEntity<ErrorDto> handleEntityAlreadyExistException(EntityAlreadyExistException exep){
        return new ResponseEntity<>(new ErrorDto(HttpStatus.NOT_FOUND, exep.getMessage(), exep),HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(ValidationException.class)
    ResponseEntity<ErrorDto> handlevalidationException(ValidationException exep) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST, exep.getMessage(), exep), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<ErrorDto> handleConstraintViolationException(ConstraintViolationException exep) {
        return new ResponseEntity<>(new ErrorDto(HttpStatus.BAD_REQUEST, exep.getMessage(), exep), HttpStatus.BAD_REQUEST);
    }
}
