package com.ssginc.secretgarden.domain.member.exception;

public class MemberNotFoundException extends RuntimeException {
    public MemberNotFoundException(String message){
        super(message);
    }
}
