package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.CelebrationRankingDto;
import com.ssginc.secretgarden.domain.celebration.dto.CreateCommentDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CommentRequestDto;
import com.ssginc.secretgarden.domain.celebration.entity.Comment;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.celebration.repository.CommentRepository;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;

    // 축하 댓글 작성
    public CreateCommentDto createComment(CommentRequestDto dto, Integer memberId, Integer celebrationId) throws IOException {

        String answer = Custom.filterCommentByGPT(dto.getContent());
        if (answer.equals("good")){ // answer 이 "good"인 경우
            Comment comment = Comment.builder()
                    .content(dto.getContent())
                    .nickname(Custom.createRandomNickname())
                    .member(memberRepository.findById(memberId).get())
                    .build();

            comment.setCelebration(celebrationRepository.findById(celebrationId).get());

            // return commentRepository.save(comment).getId();
            return CreateCommentDto.builder()
                    .id(commentRepository.save(comment).getId())
                    .answer(answer)
                    .build();
        }
        // answer 이 "bad"인 경우
        return CreateCommentDto.builder()
                .id(-1)
                .answer(answer)
                .build();
    }

    // 축하 댓글 삭제
    public void deleteComment(Integer commentId){
        Integer commentMemberId = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with id: " + commentId))
                .getMember().getId();

        commentRepository.deleteById(commentId);
    }

    // 이 달의 축하 계열사
    public List<CelebrationRankingDto> findTopCommentersThisMonth() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfThisMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endOfThisMonth = now.with(TemporalAdjusters.lastDayOfMonth()).withHour(23).withMinute(59).withSecond(59);

        /*// 오늘 날짜를 2월 1일로 고정합니다.
        LocalDate fixedToday = LocalDate.of(2024, 2, 1); // 예시 날짜, 실제 테스트 날짜로 변경해주세요.
        // 고정된 오늘 날짜를 기반으로 현재 달의 시작과 끝을 계산합니다.
        YearMonth currentMonth = YearMonth.from(fixedToday);
        LocalDateTime startOfThisMonth = currentMonth.atDay(1).atStartOfDay();
        LocalDateTime endOfThisMonth = currentMonth.atEndOfMonth().atTime(23, 59, 59);*/

        List<Object[]> results = commentRepository.findTopCommentingCompaniesByDateRange(startOfThisMonth, endOfThisMonth);

        // 상위 3개의 결과만 처리
        AtomicInteger rank = new AtomicInteger(1); // 순위 1부터 시작

        return results.stream()
                .limit(3) // 상위 3개의 회사만 선택
                .map(result -> {
                    String companyName = (String) result[0];
                    Long commentCount = (Long) result[1];
                    return CelebrationRankingDto.builder()
                            .rank(rank.getAndIncrement()) // 현재 순위 사용하고, 1 증가
                            .company(companyName)
                            .count(commentCount)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
