package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.DetailCelebrationDto;
import com.ssginc.secretgarden.domain.celebration.dto.ListDetailCelebrationDto;
import com.ssginc.secretgarden.domain.celebration.dto.ListResponseDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.dto.response.CelebrationResponseDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.service.CelebrationService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CelebrationController {

    private final CelebrationService celebrationService;

    // 축하 게시글 작성
    @PostMapping("/celebration/{memberId}")
    public CelebrationResponseDto createCelebration (
            @RequestBody CelebrationRequestDto dto,
            @PathVariable("memberId") Integer memberId) throws IOException{

        return CelebrationResponseDto.builder()
                .id(celebrationService.createCelebration(dto, memberId))
                .build();
    }

    // 축하 리스트 조회
    @GetMapping("/celebration")
    public ListResponseDto getAllCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        List<ListDetailCelebrationDto> allCelebrations = findCelebrations.stream()
                .map(ListDetailCelebrationDto::toDto)
                .sorted(Comparator.comparing(ListDetailCelebrationDto::getId).reversed())
                .toList();

        return ListResponseDto.builder()
                .list(allCelebrations)
                .build();
    }

    // 축하 리스트 조회 (오늘의 축하) - daily
    @GetMapping("/celebration/daily")
    public ListResponseDto getDailyCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        List<ListDetailCelebrationDto> dailyCelebrations = findCelebrations.stream()
                .filter(c -> "daily".equals(c.getCategory()))
                .map(ListDetailCelebrationDto::toDto)
                .sorted(Comparator.comparing(ListDetailCelebrationDto::getId).reversed())
                .toList();

        return ListResponseDto.builder()
                .list(dailyCelebrations)
                .build();
    }

    // 축하 리스트 조회 (오늘의 기념일) - anniversary
    @GetMapping("/celebration/anniversary")
    public ListResponseDto getAnniversaryCelebrationList(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        List<ListDetailCelebrationDto> anniversaryCelebrations = findCelebrations.stream()
                .filter(c -> "anniversary".equals(c.getCategory()))
                .map(ListDetailCelebrationDto::toDto)
                .sorted(Comparator.comparing(ListDetailCelebrationDto::getId).reversed())
                .toList();

        return ListResponseDto.builder()
                .list(anniversaryCelebrations)
                .build();
    }

    // 축하 미리보기 (3개)
    @GetMapping("celebration/preview")
    public ListResponseDto getCelebrationPreview(){

        List<Celebration> findCelebrations = celebrationService.findCelebrations();

        List<ListDetailCelebrationDto> previewCelebrations = findCelebrations.stream()
                .sorted(Comparator.comparing(Celebration::getId).reversed()) // id 기준으로 내림차순 정렬
                .limit(3)
                .map(ListDetailCelebrationDto::toDto)
                .sorted(Comparator.comparing(ListDetailCelebrationDto::getId).reversed())
                .toList();

        return ListResponseDto.builder()
                .list(previewCelebrations)
                .build();
    }
    
    // 축하 게시글 상세 조회
    @GetMapping("celebration/{celebrationId}")
    public DetailCelebrationDto getDetailCelebration(
            @PathVariable("celebrationId") Integer celebrationId){

        return celebrationService.findOneCelebration(celebrationId);
    }

    // 축하 게시글 삭제
    @DeleteMapping("/celebration/{celebrationId}")
    public void deleteCelebration(
            @PathVariable("celebrationId") Integer celebrationId){
        celebrationService.deleteCelebration(celebrationId);
    }
}
