package com.ssginc.secretgarden.domain.celebration.controller;

import com.ssginc.secretgarden.domain.celebration.dto.CelebrationRankingDto;
import com.ssginc.secretgarden.domain.celebration.dto.CreateCommentDto;
import com.ssginc.secretgarden.domain.celebration.dto.RankingResponseDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CommentRequestDto;
import com.ssginc.secretgarden.domain.celebration.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 축하 댓글 작성
    @PostMapping("/celebration/comment/{celebrationId}/{memberId}")
    public CreateCommentDto createComment(
            @RequestBody CommentRequestDto dto,
            @PathVariable("celebrationId") Integer celebrationId,
            @PathVariable("memberId") Integer memberId) throws IOException {

        return commentService.createComment(dto, memberId, celebrationId);
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
