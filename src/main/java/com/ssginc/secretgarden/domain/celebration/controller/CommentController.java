package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.CelebrationRankingDto;
import com.ssginc.secretgarden.domain.celebration.dto.ListResponseDto;
import com.ssginc.secretgarden.domain.celebration.dto.RankingResponseDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CommentRequestDto;
import com.ssginc.secretgarden.domain.celebration.dto.response.CommentResponseDto;
import com.ssginc.secretgarden.domain.celebration.service.CommentService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final JwtUtil jwtUtil;

    // 축하 댓글 작성
    @PostMapping("/celebration/comment/{celebrationId}/{memberId}")
    public CommentResponseDto createComment(
            @RequestBody CommentRequestDto dto,
            @RequestHeader("Authorization") String authorizationHeader,
            @PathVariable("celebrationId") Integer celebrationId,
            @PathVariable("memberId") Integer memberId){

        return CommentResponseDto.builder()
                .id(commentService.createComment(dto, memberId, celebrationId))
                .build();
    }

    // 축하 댓글 삭제
    @DeleteMapping("/celebration/comment/{commentId}")
    public void deleteComment(@PathVariable Integer commentId){
        commentService.deleteComment(commentId);
    }

    @GetMapping("celebration/ranking")
    public RankingResponseDto getCelebrationRanking(){
        List<CelebrationRankingDto> rankingDtoList = commentService.findTopCommentersLastMonth();

        return RankingResponseDto.builder()
                .rankingList(rankingDtoList)
                .build();
    }

}
