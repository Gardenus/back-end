package com.ssginc.secretgarden.domain.member.controller;

import com.ssginc.secretgarden.domain.member.dto.request.LoginRequest;
import com.ssginc.secretgarden.domain.member.dto.request.SignupRequest;
import com.ssginc.secretgarden.domain.member.dto.response.LoginResponse;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.service.MemberService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequestDto){
        Boolean idCheck = memberService.idCheck(signupRequestDto.getBlossomId());
        if(!idCheck){
            return new ResponseEntity<>("중복된 ID입니다.", HttpStatus.CONFLICT);
        }
        memberService.signup(signupRequestDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.",HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String blossomId = loginRequest.getBlossomId();
        String password = loginRequest.getPassword();
        Member member = memberService.login(blossomId, password);
        String token = jwtUtil.generateToken(memberService.getMember(blossomId));
        LoginResponse loginResponse = LoginResponse.builder()
                            .blossomId(member.getBlossomId())
                .companyName(member.getCompany().getName())
                .companyId(member.getCompany().getId())
                .memberId(member.getId())
                .name(member.getName())
                .token(token)
                .build();
        return new ResponseEntity<>(loginResponse, HttpStatus.ACCEPTED);
    }

}
