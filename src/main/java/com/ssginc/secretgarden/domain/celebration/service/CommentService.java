package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.request.CommentRequestDto;
import com.ssginc.secretgarden.domain.celebration.entity.Comment;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.celebration.repository.CommentRepository;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;

    public Integer createComment(CommentRequestDto dto, Integer memberId, Integer celebrationId){

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .nickname(Custom.createRandomNickname())
                .member(memberRepository.findById(memberId).get())
                .build();

        comment.setCelebration(celebrationRepository.findById(celebrationId).get());

        return commentRepository.save(comment).getId();
    }
}
