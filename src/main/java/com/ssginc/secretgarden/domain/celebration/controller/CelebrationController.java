package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.DetailCelebrationDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.dto.response.CelebrationResponseDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.celebration.service.CelebrationService;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CelebrationController {

    private final CelebrationService celebrationService;
    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    // 축하 게시글 작성
    @PostMapping("/celebration")
    public CelebrationResponseDto createCelebration(
            @RequestBody CelebrationRequestDto dto,
            @RequestHeader("Authorization") String authorizationHeader){

        Integer memberId = jwtUtil.getMemberIdByToken(authorizationHeader);

        return CelebrationResponseDto.builder()
                .id(celebrationService.createCelebration(dto, memberId))
                .build();
    }

    // 축하 게시글 리스트
    @GetMapping("/celebration")
    public List<DetailCelebrationDto> getAllCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        return findCelebrations.stream()
                .map(c -> {
                    return DetailCelebrationDto.toDto(c, c.getMember());})
                .collect(Collectors.toList());
    }
    
    // 축하 게시글 상세
    /*@GetMapping("celebration/{celebrationId}")
    public DetailCelebrationDto getDetailCelebration(
            @PathVariable("celebrationId")Integer celebrationId){


    }*/
}
