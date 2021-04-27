package com.capgemini.banco.settings.exceptions.config;

import com.capgemini.banco.settings.exceptions.BadRequestException;
import com.capgemini.banco.settings.exceptions.NotFoundException;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String TRACE = "trace";

    @Value("${reflectoring.trace:false}")
    private boolean printStackTrace;

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse handleAllUncaughtException(RuntimeException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponse handleNotFoundException(NotFoundException notFoundException, WebRequest request) {
        return buildErrorResponse(notFoundException, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse handleBadRequestException(BadRequestException exception, WebRequest request) {
        return buildErrorResponse(exception, HttpStatus.BAD_REQUEST, request);
    }

    private ErrorResponse buildErrorResponse(Exception exception, HttpStatus status, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(status.value(), exception.getMessage());

        if(printStackTrace || isTraceOn(request)){
            errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
        }

        return errorResponse;
    }

    private boolean isTraceOn(WebRequest request) {
        String [] value = request.getParameterValues(TRACE);
        return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
    }
}
