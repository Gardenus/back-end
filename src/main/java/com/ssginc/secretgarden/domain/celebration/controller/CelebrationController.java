package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.dto.response.CelebrationResponseDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.service.CelebrationService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CelebrationController {

    private final CelebrationService celebrationService;
    private final JwtUtil jwtUtil;

    // 축하 게시글 작성
    @PostMapping("/api/celebration")
    public CelebrationResponseDto createCelebration(
            @RequestBody CelebrationRequestDto dto,
            @RequestHeader("Authorization") String authorizationHeader){

        Integer memberId = jwtUtil.getMemberIdByToken(authorizationHeader);

        return CelebrationResponseDto.builder()
                .id(celebrationService.createCelebration(dto, memberId))
                .build();
    }

    /*@PostMapping("/createImage")
    public ResponseEntity<String> createImage(@RequestBody String message){
        JavaAI javaAI = JavaAI.javaAiBuilder("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");
        String imgUrl = javaAI.generateImage(message);
        return new ResponseEntity<String>(imgUrl, HttpStatus.OK);
    }

    @PostMapping("/createImage2")
    public ResponseEntity<String> createImage2(@RequestBody String message){
        String imgUrl = chatgptService.imageGenerate(message);
        return new ResponseEntity<String>(imgUrl, HttpStatus.OK);
    }

    @PostMapping("/createImage3")
    public ResponseEntity<String> createImage3(@RequestBody String message){
        String imgUrl = chatgptService.imageGenerate(message);
        return new ResponseEntity<String>(imgUrl, HttpStatus.OK);
    }*/
}
