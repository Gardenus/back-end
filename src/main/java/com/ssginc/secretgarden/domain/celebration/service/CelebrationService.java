package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.DetailCelebrationDto;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CelebrationService {

    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;

    // 축하 게시글 작성
    public Integer createCelebration(CelebrationRequestDto dto, Integer memberId){

        String nickname = "";
        if (dto.getIsSecret()) nickname = createRandomNickname();
        else nickname = memberRepository.findById(memberId).get().getName(); 
            
        Celebration celebration = Celebration.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .isSecret(dto.getIsSecret())
                .nickname(nickname)
                .category("daily")
                .member(memberRepository.findById(memberId).get())
                .build();

        return celebrationRepository.save(celebration).getId();
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
                        .nickname("신세계")
                        .category("anniversary")
                        .member(memberRepository.findByBlossomId("admin"))
                        .createdAt(LocalDateTime.now())
                        .build();
                celebrationRepository.save(celebration);
            }
        }
    }

    @Scheduled(cron = "0 10 10 * * *") // 매일 00시 00분에 자동 실행
    public void scheduleBirthdayCelebrations() {
        createCelebrationByBirthDate();
    }

    // 축하 리스트 조회
    public List<Celebration> findCelebrations(){
        return celebrationRepository.findAll();
    }

    // 축하 상세 조회
    public DetailCelebrationDto findOneCelebration(Integer celebrationId){
        Celebration findCelebration = celebrationRepository.findById(celebrationId).get();
        return DetailCelebrationDto.toDto(findCelebration);
    }

    // 축하 게시글 삭제
    public void deleteCelebration(Integer celebrationId, Integer memberId){
        Integer celebrationMemberId = celebrationRepository.findById(celebrationId)
                .orElseThrow(() -> new IllegalArgumentException("Celebration not found with id: " + celebrationId))
                .getMember().getId();

        // 게시글 작성자만 해당 게시글 삭제 가능
        if (!celebrationMemberId.equals(memberId)){
            throw new IllegalArgumentException("게시글 작성자만 게시글을 삭제할 수 있습니다.");
        }
        celebrationRepository.deleteById(celebrationId);
    }

    // 익명 닉네임 랜덤 생성 메서드
    public static String createRandomNickname() {
        List<String> nick = Arrays.asList(
                "기분좋은", "행복한", "즐거운", "멋진", "아름다운",
                "빛나는", "활기찬", "따뜻한", "매력적인", "상쾌한",
                "우아한", "평화로운", "기쁜", "환상적인", "감동적인",
                "밝은", "희망찬", "달콤한", "사랑스러운", "신나는",
                "안정된", "만족스러운", "편안한", "활발한", "용기있는",
                "창의적인", "능력있는", "성실한", "정직한", "친절한"
        );
        List<String> name = Arrays.asList(
                "코스모스", "장미", "튤립", "라일락", "해바라기",
                "수국", "데이지", "카네이션", "벚꽃", "라벤더",
                "금잔화", "아네모네", "팬지", "프리지아", "난초",
                "백합", "아마릴리스", "철쭉", "연꽃", "진달래",
                "동백", "매화", "국화", "히야신스", "목련",
                "포인세티아", "피튜니아", "거베라", "아이리스", "자스민"
        );
        Collections.shuffle(nick); Collections.shuffle(name);
        return nick.get(0) + " " + name.get(0);
    }
}
