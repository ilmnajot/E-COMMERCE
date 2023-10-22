package uz.ilmnajot.registration.exception;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class AllExceptionHandler {

    @ExceptionHandler(AppException.class)
    public HttpEntity<?> handleAppException(AppException appException, WebRequest webRequest) {

        ErrorDetails errorDetails = new ErrorDetails(new Date(), appException.getMessage(), webRequest.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
