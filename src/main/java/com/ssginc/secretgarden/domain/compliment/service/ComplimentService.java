package com.ssginc.secretgarden.domain.compliment.service;

import com.ssginc.secretgarden.domain.compliment.dto.ComplimentRankingDto;
import com.ssginc.secretgarden.domain.compliment.dto.request.WriteComplimentRequest;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import com.ssginc.secretgarden.domain.compliment.exception.ComplimentNotFoundException;
import com.ssginc.secretgarden.domain.compliment.repository.ComplimentRepository;
import com.ssginc.secretgarden.domain.member.entity.Company;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.exception.MemberNotFoundException;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplimentService {

    private final MemberRepository memberRepository;
    private final ComplimentRepository complimentRepository;

    public void writeCompliment(Integer memberId, WriteComplimentRequest writeComplimentRequest) {
        Member writer = memberRepository.findById(memberId)
                .orElseThrow(()-> new RuntimeException("칭찬 작성자 id가 존재하지 않습니다."));
        Member receiver = memberRepository.findFirstByNameOrderByIdAsc(writeComplimentRequest.getName())
                .orElseThrow(() -> new MemberNotFoundException("칭찬 대상자가 존재하지 않습니다."));
        Compliment compliment = Compliment.builder()
                .member(writer)
                .receiverId(receiver.getId())
                .category(writeComplimentRequest.getCategory())
                .content(writeComplimentRequest.getContent())
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

    public List<ComplimentRankingDto> getComplimentRanking() {
        int currentMonth = LocalDateTime.now().getMonthValue();
        return complimentRepository.findTop3Member(currentMonth);
    }

    public void deleteCompliment(Integer complimentId) {
        Compliment compliment = complimentRepository.findById(complimentId)
                .orElseThrow(()->new ComplimentNotFoundException("존재하지 않는 칭찬글 id 입니다."));
        complimentRepository.delete(compliment);
    }

    public List<Compliment> getReceivedCompliment(Integer memberId) {
        return complimentRepository.findByReceiverIdOrderByCreatedAtDesc(memberId);
    }

    public List<Compliment> getSentCompliment(Integer memberId) {
        return complimentRepository.findByMemberIdOrderByCreatedAtDesc(memberId);
    }
}
