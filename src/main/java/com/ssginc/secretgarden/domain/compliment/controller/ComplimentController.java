package com.ssginc.secretgarden.domain.compliment.controller;

import com.ssginc.secretgarden.domain.compliment.dto.ComplimentRankingDto;
import com.ssginc.secretgarden.domain.compliment.dto.request.WriteComplimentRequest;
import com.ssginc.secretgarden.domain.compliment.dto.response.ComplimentRankingResponse;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import com.ssginc.secretgarden.domain.compliment.service.ComplimentService;
import com.ssginc.secretgarden.domain.member.entity.Company;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import com.ssginc.secretgarden.domain.member.service.MemberService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compliment")
public class ComplimentController {
    private final ComplimentService complimentService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    @PostMapping("/{memberId}")
    public ResponseEntity<?> writeCompliment(@RequestBody WriteComplimentRequest writeComplimentRequest,
                                             @PathVariable("memberId") Integer memberId){
         complimentService.writeCompliment(memberId, writeComplimentRequest);
         return new ResponseEntity<>("칭찬글 작성이 완료되었습니다." , HttpStatus.CREATED);
    }

    @DeleteMapping("/{complimentId}")
    public ResponseEntity<?> deleteCompliment(@PathVariable("complimentId") Integer complimentId){
        complimentService.deleteCompliment(complimentId);
        return new ResponseEntity<>("칭찬글이 삭제되었습니다.", HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllComplimentList(){
        List<Compliment> allComplimentList = complimentService.getAllComplimentList();
        return new ResponseEntity<>(allComplimentList, HttpStatus.OK);
    }
    @GetMapping("/business")
    public ResponseEntity<?> getBizComplimentList(){
        List<Compliment> bizComplimentList = complimentService.getBizComplimentList();
        return new ResponseEntity<>(bizComplimentList, HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyComplimentList(){
        List<Compliment> dailyComplimentList = complimentService.getDailyComplimentList();
        return new ResponseEntity<>(dailyComplimentList, HttpStatus.OK);
    }

    @GetMapping("/preview")
    public ResponseEntity<?> getComplimentPreview(){
        List<Compliment> complimentPreview = complimentService.getComplimentPreview();
        return new ResponseEntity<>(complimentPreview, HttpStatus.OK);
    }

    @GetMapping("/ranking")
    public ResponseEntity<?> getComplimentRanking(){
        List<ComplimentRankingDto> rankingDtoList = complimentService.getComplimentRanking();
        List<ComplimentRankingResponse> rankingResponse = rankingDtoList.stream()
                .map(complimentRankingDto -> {
                    Member member = memberService.getMemberByMemberId(complimentRankingDto.getMemberId());
                    return ComplimentRankingResponse.builder()
                            .companyId(member.getCompany().getId())
                            .companyName(member.getCompany().getName())
                            .name(member.getName())
                            .count(complimentRankingDto.getCount())
                            .rank(complimentRankingDto.getRank())
                            .memberId(complimentRankingDto.getMemberId())
                            .build();
                }).collect(Collectors.toList());
        return new ResponseEntity<>(rankingResponse, HttpStatus.OK);
    }
}
