package com.ssginc.secretgarden.domain.member.controller;

import com.ssginc.secretgarden.domain.member.dto.request.LoginRequestDto;
import com.ssginc.secretgarden.domain.member.dto.request.SignupRequestDto;
import com.ssginc.secretgarden.domain.member.service.MemberService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequestDto signupRequestDto){
        Boolean idCheck = memberService.idCheck(signupRequestDto.getBlossomId());
        if(!idCheck){
            return new ResponseEntity<>("중복된 ID입니다.", HttpStatus.CONFLICT);
        }
        memberService.signup(signupRequestDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        String blossomId = loginRequestDto.getBlossomId();
        String password = loginRequestDto.getPassword();
        if (memberService.login(blossomId, password)) {
            String token = jwtUtil.generateToken(blossomId);
            return new ResponseEntity<>(token, HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>("ID 또는 비밀번호가 잘못 되었습니다.", HttpStatus.UNAUTHORIZED);
    }

}
