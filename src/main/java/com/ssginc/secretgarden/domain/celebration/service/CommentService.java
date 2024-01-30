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

    // 축하 댓글 작성
    public Integer createComment(CommentRequestDto dto, Integer memberId, Integer celebrationId){

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .nickname(Custom.createRandomNickname())
                .member(memberRepository.findById(memberId).get())
                .build();

        comment.setCelebration(celebrationRepository.findById(celebrationId).get());

        return commentRepository.save(comment).getId();
    }

    // 축하 댓글 삭제
    public void deleteComment(Integer commentId, Integer memberId){
        Integer commentMemberId = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId))
                .getMember().getId();

        // 댓글 작성자만 해당 댓글 삭제 가능
        if (!commentMemberId.equals(memberId)){
            throw new IllegalArgumentException("댓글 작성자만 댓글을 삭제할 수 있습니다.");
        }
        commentRepository.deleteById(commentId);
    }
}
