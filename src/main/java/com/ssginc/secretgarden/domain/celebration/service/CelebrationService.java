package com.ssginc.secretgarden.domain.celebration.service;

import com.ssginc.secretgarden.domain.celebration.dto.request.CelebrationRequestDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.celebration.repository.CelebrationRepository;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
                .category("소소")
                .member(memberRepository.findById(memberId).get())
                .build();

        return celebrationRepository.save(celebration).getId();
    }

    public List<Celebration> findCelebrations(){
        return celebrationRepository.findAll();
    }
}
