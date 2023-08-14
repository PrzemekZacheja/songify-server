package com.songify.apivalidation;

import com.songify.song.controller.SongRestController;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice(assignableTypes = SongRestController.class)
public class ApiValidationErrorHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(BAD_REQUEST)
    public ApiValidationErrorResponseDto handleValidationException(MethodArgumentNotValidException exception) {
        List<String> errorsFromException = getErrorsFromException(exception);
        return new ApiValidationErrorResponseDto(errorsFromException,  BAD_REQUEST);
    }

    private List<String> getErrorsFromException(MethodArgumentNotValidException exception) {
        return exception.getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(error -> error.getField() + " : " + error.getDefaultMessage())
                        .toList();
    }
}