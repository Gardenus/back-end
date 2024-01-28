package com.ssginc.secretgarden.domain.compliment.service;

import com.ssginc.secretgarden.domain.compliment.dto.request.WriteComplimentRequest;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import com.ssginc.secretgarden.domain.compliment.repository.ComplimentRepository;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplimentService {

    private final MemberRepository memberRepository;
    private final ComplimentRepository complimentRepository;

    public void writeCompliment(Integer memberId, WriteComplimentRequest writeComplimentRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("해당 회원 id가 존재하지 않습니다."));
        Compliment compliment = Compliment.builder()
                .member(member)
                .category(writeComplimentRequest.getCategory())
                .content(writeComplimentRequest.getContent())
                .receiver_id(writeComplimentRequest.getReceiverId())
                .build();
        complimentRepository.save(compliment);
    }

    public List<Compliment> getComplimentPreview() {
        return complimentRepository.findTop3ByOrderByCreatedAtDesc();
    }

    public List<Compliment> getAllComplimentList() {
        return complimentRepository.findAllByOrderByCreatedAtDesc();
    }
    public List<Compliment> getDailyComplimentList() {
        return complimentRepository.findByCategoryOrderByCreatedAtDesc("daily");
    }

    public List<Compliment> getBizComplimentList() {
        return complimentRepository.findByCategoryOrderByCreatedAtDesc("business");
    }

}
