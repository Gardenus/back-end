package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.DetailCelebrationDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.dto.response.CelebrationResponseDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.service.CelebrationService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CelebrationController {

    private final CelebrationService celebrationService;
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

    // 축하 리스트 조회
    @GetMapping("/celebration")
    public List<DetailCelebrationDto> getAllCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        return findCelebrations.stream()
                .map(DetailCelebrationDto::toDto)
                .collect(Collectors.toList());
    }

    // 축하 리스트 조회 (오늘의 축하) - daily
    @GetMapping("/celebration/daily")
    public List<DetailCelebrationDto> getDailyCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        return findCelebrations.stream()
                .filter(c -> "daily".equals(c.getCategory()))
                .map(DetailCelebrationDto::toDto)
                .collect(Collectors.toList());
    }

    // 축하 리스트 조회 (오늘의 기념일) - anniversary
    @GetMapping("/celebration/anniversary")
    public List<DetailCelebrationDto> getAnniversaryCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        return findCelebrations.stream()
                .filter(c -> "anniversary".equals(c.getCategory()))
                .map(DetailCelebrationDto::toDto)
                .collect(Collectors.toList());
    }
    
    // 축하 게시글 상세 조회
    @GetMapping("celebration/{celebrationId}")
    public DetailCelebrationDto getDetailCelebration(
            @PathVariable("celebrationId") Integer celebrationId){

        return celebrationService.findOneCelebration(celebrationId);
    }
}
