package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.DetailCelebrationDto;
import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import com.ssginc.secretgarden.global.util.S3Uploader;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class CelebrationService {

    private final CelebrationRepository celebrationRepository;
    private final MemberRepository memberRepository;
    private final S3Uploader s3Uploader;

    // 축하 게시글 작성
    public Integer createCelebration(CelebrationRequestDto dto, Integer memberId) throws IOException {

        System.out.println("dto content : " + dto.getContent());

        String originalContent = dto.getContent();

        Celebration celebration = Celebration.builder()
                .content(dto.getContent().replace("\n", " "))
                .build();
        Celebration savedCelebration = celebrationRepository.save(celebration);

        System.out.println("save content : " + savedCelebration.getContent());

        String nickname = "";
        if (dto.getIsSecret()) nickname = Custom.createRandomNickname();
        else nickname = memberRepository.findById(memberId).get().getName();

        String gptImageUrl = Custom.createImageByGPT("daily", savedCelebration.getContent());
        String keyName = "gpt-image/"+UUID.randomUUID().toString() + ".png";
        s3Uploader.uploadImageToS3(gptImageUrl,"secretgarden-bucket",keyName);
        String s3Url = "https://secretgarden-bucket.s3.ap-northeast-2.amazonaws.com/" + keyName;

        savedCelebration.setTitle(dto.getTitle());
        savedCelebration.setContent(originalContent);
        savedCelebration.setIsSecret(dto.getIsSecret());
        savedCelebration.setNickname(nickname);
        savedCelebration.setCategory("daily");
        savedCelebration.setMember(memberRepository.findById(memberId).get());
        savedCelebration.setImageUrl(s3Url);

        return savedCelebration.getId();
    }

    // 축하 게시글 자동 작성 (오늘의 기념일)
    public void createCelebrationByBirthDate() throws IOException{
        LocalDate today = LocalDate.now();

        List<Member> birthdayMembers
                = memberRepository.findByMonthAndDay(today.getMonthValue(), today.getDayOfMonth());



        for (Member member : birthdayMembers) {
            if (!member.getBlossomId().equals("admin")){
                String content = "오늘은 " + member.getName() + "님의 생일입니다~~" + "모두 함께 축하해주세요~~!!";
                String gptImageUrl = Custom.createImageByGPT("anniversary", content);
                String keyName = "gpt-image/"+UUID.randomUUID().toString() + ".png";
                s3Uploader.uploadImageToS3(gptImageUrl,"secretgarden-bucket",keyName);
                String s3Url = "https://secretgarden-bucket.s3.ap-northeast-2.amazonaws.com/" + keyName;
                Celebration celebration = Celebration.builder()
                        .title(member.getName() + " 님의 생일을 축하합니다!")
                        .content(content)
                        .isSecret(false)
                        .nickname("신세계")
                        .category("anniversary")
                        .member(memberRepository.findByBlossomId("admin"))
                        .imageUrl(s3Url)
                        .createdAt(LocalDateTime.now())
                        .build();
                celebrationRepository.save(celebration);
            }
        }
    }

    @Scheduled(cron = "0 21 10 * * *") // 매일 06시 00분에 자동 실행
    public void scheduleBirthdayCelebrations() throws IOException{
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
    public void deleteCelebration(Integer celebrationId){
        Integer celebrationMemberId = celebrationRepository.findById(celebrationId)
                .orElseThrow(() -> new IllegalArgumentException("Celebration not found with id: " + celebrationId))
                .getMember().getId();
        celebrationRepository.deleteById(celebrationId);
    }

    public List<Celebration> getSentCelebration(Integer memberId) {
        return celebrationRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }
}
