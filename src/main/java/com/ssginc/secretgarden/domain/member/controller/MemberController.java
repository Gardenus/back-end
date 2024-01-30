package com.ssginc.secretgarden.domain.member.controller;

import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.service.CelebrationService;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import com.ssginc.secretgarden.domain.compliment.service.ComplimentService;
import com.ssginc.secretgarden.domain.member.dto.request.LoginRequest;
import com.ssginc.secretgarden.domain.member.dto.request.SignupRequest;
import com.ssginc.secretgarden.domain.member.dto.response.CelebrationResponse;
import com.ssginc.secretgarden.domain.member.dto.response.ComplimentResponse;
import com.ssginc.secretgarden.domain.member.dto.response.LoginResponse;
import com.ssginc.secretgarden.domain.member.dto.response.MemberResponse;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.service.MemberService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final ComplimentService complimentService;
    private final CelebrationService celebrationService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequestDto) {
        Boolean idCheck = memberService.idCheck(signupRequestDto.getBlossomId());
        if (!idCheck) {
            return new ResponseEntity<>("중복된 ID 입니다.", HttpStatus.CONFLICT);
        }
        memberService.signup(signupRequestDto);
        return new ResponseEntity<>("회원가입이 완료되었습니다.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        String blossomId = loginRequest.getBlossomId();
        String password = loginRequest.getPassword();
        Member member = memberService.login(blossomId, password);
        String token = jwtUtil.generateToken(memberService.getMemberByBlossomId(blossomId));
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

    @GetMapping("/{memberId}")
    public ResponseEntity<?> myInfo(@PathVariable("memberId") Integer memberId) {
        Member member = memberService.getMemberByMemberId(memberId);
        MemberResponse memberResponse = MemberResponse.builder()
                .memberId(member.getId())
                .name(member.getName())
                .companyId(member.getCompany().getId())
                .companyName(member.getCompany().getName())
                .build();
        return new ResponseEntity<>(memberResponse, HttpStatus.OK);
    }

    @GetMapping("/compliment/receive/{memberId}")
    public ResponseEntity<?> getMyReceivedCompliment(@PathVariable("memberId") Integer memberId) {
        List<Compliment> complimentList = complimentService.getReceivedCompliment(memberId);
        Member member = memberService.getMemberByMemberId(memberId);
        List<ComplimentResponse> response = complimentList.stream()
                .map(compliment -> ComplimentResponse.builder()
                        .complimentId(compliment.getId())
                        .category(compliment.getCategory())
                        .content(compliment.getContent())
                        .name(member.getName())
                        .companyName(member.getCompany().getName())
                        .build()).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/compliment/send/{memberId}")
    public ResponseEntity<?> getMySentCompliment(@PathVariable("memberId") Integer memberId) {
        List<Compliment> complimentList = complimentService.getSentCompliment(memberId);
        List<ComplimentResponse> response = complimentList.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/celebration/send/{memberId}")
    public ResponseEntity<?> getMySentCelebration(@PathVariable("memberId") Integer memberId){
        List<Celebration> celebrationList = celebrationService.getSentCelebration(memberId);
        List<CelebrationResponse> response = celebrationList.stream()
                .map(celebration ->
                        {
                            return CelebrationResponse.builder()
                                    .celebrationId(celebration.getId())
                                    .title(celebration.getTitle())
                                    .nickname(celebration.getNickname())
                                    .category(celebration.getCategory())
                                    .build();
                        }
                ).collect(Collectors.toList());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
