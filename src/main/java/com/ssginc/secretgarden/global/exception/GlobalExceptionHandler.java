package com.ssginc.secretgarden.global.exception;

import com.ssginc.secretgarden.domain.compliment.exception.ComplimentNotFoundException;
import com.ssginc.secretgarden.domain.member.exception.MemberNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ComplimentNotFoundException.class, MemberNotFoundException.class})
    public ResponseEntity<String> handleNotFoundException(RuntimeException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}