package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CelebrationService {

    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;

    // 축하 게시글 작성
    public Integer createCelebration(CelebrationRequestDto dto, Integer memberId){

        Celebration celebration = Celebration.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .isSecret(dto.getIsSecret())
                .category("축하")
                .member(memberRepository.findById(memberId).get())
                .build();

        return celebrationRepository.save(celebration).getId();
    }

    // 축하 리스트 조회
    public List<Celebration> findCelebrations(){
        return celebrationRepository.findAll();
    }

    // 축하 게시글 자동 작성 (오늘의 기념일)
    public void createCelebrationByBirthDate(){
        LocalDate today = LocalDate.now();

        List<Member> birthdayMembers
                = memberRepository.findByMonthAndDay(today.getMonthValue(), today.getDayOfMonth());

        for (Member member : birthdayMembers) {
            if (!member.getBlossomId().equals("admin")){
                Celebration celebration = Celebration.builder()
                        .title(member.getName() + "님의 생일을 축하합니다!")
                        .content("오늘은 " + member.getName() + "님의 생일입니다~~"
                                + "모두 함께 축하해주세요~~!!")
                        .isSecret(false)
                        .category("기념일")
                        .member(memberRepository.findByBlossomId("admin"))
                        .createdAt(LocalDateTime.now())
                        .build();
                celebrationRepository.save(celebration);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // 매일 00시 00분에 자동 실행
    public void scheduleBirthdayCelebrations() {
        createCelebrationByBirthDate();
    }
}
