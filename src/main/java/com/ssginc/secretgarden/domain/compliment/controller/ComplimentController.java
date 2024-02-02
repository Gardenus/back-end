package com.ssginc.secretgarden.domain.compliment.controller;

import com.ssginc.secretgarden.domain.compliment.dto.ComplimentRankingDto;
import com.ssginc.secretgarden.domain.compliment.dto.CreateComplimentDto;
import com.ssginc.secretgarden.domain.compliment.dto.TodayChallengeDto;
import com.ssginc.secretgarden.domain.compliment.dto.request.WriteComplimentRequest;
import com.ssginc.secretgarden.domain.compliment.dto.response.ComplimentListResponse;
import com.ssginc.secretgarden.domain.compliment.dto.response.ComplimentRankingListResponse;
import com.ssginc.secretgarden.domain.compliment.dto.response.ComplimentRankingResponse;
import com.ssginc.secretgarden.domain.compliment.entity.Compliment;
import com.ssginc.secretgarden.domain.compliment.service.ComplimentService;
import com.ssginc.secretgarden.domain.member.dto.response.ComplimentResponse;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.service.MemberService;
import com.ssginc.secretgarden.global.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/compliment")
public class ComplimentController {
    private final ComplimentService complimentService;
    private final MemberService memberService;
    private final JwtUtil jwtUtil;

    /*@PostMapping("/{memberId}")
    public ResponseEntity<?> writeCompliment(@RequestBody WriteComplimentRequest writeComplimentRequest,
                                             @PathVariable("memberId") Integer memberId){
         complimentService.writeCompliment(memberId, writeComplimentRequest);
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>("칭찬글 작성이 완료되었습니다.",resHeaders,HttpStatus.CREATED);
    }*/

    @PostMapping("/{memberId}")
    public CreateComplimentDto writeCompliment(@RequestBody WriteComplimentRequest writeComplimentRequest,
                                               @PathVariable("memberId") Integer memberId) throws IOException {

        return complimentService.writeCompliment(memberId, writeComplimentRequest);
    }

    @DeleteMapping("/{complimentId}")
    public ResponseEntity<?> deleteCompliment(@PathVariable("complimentId") Integer complimentId){
        complimentService.deleteCompliment(complimentId);
        HttpHeaders resHeaders = new HttpHeaders();
        resHeaders.add("Content-Type", "application/json;charset=UTF-8");
        return new ResponseEntity<>("칭찬글이 삭제되었습니다.", resHeaders, HttpStatus.CREATED);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllComplimentList(){
        List<Compliment> allComplimentList = complimentService.getAllComplimentList();
        List<ComplimentResponse> response = allComplimentList.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        ComplimentListResponse complimentListResponse = new ComplimentListResponse(response);
        return new ResponseEntity<>(complimentListResponse, HttpStatus.OK);
    }
    @GetMapping("/business")
    public ResponseEntity<?> getBizComplimentList(){
        List<Compliment> bizComplimentList = complimentService.getBizComplimentList();
        List<ComplimentResponse> response = bizComplimentList.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        ComplimentListResponse complimentListResponse = new ComplimentListResponse(response);
        return new ResponseEntity<>(complimentListResponse, HttpStatus.OK);
    }

    @GetMapping("/daily")
    public ResponseEntity<?> getDailyComplimentList(){
        List<Compliment> dailyComplimentList = complimentService.getDailyComplimentList();
        List<ComplimentResponse> response = dailyComplimentList.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        ComplimentListResponse complimentListResponse = new ComplimentListResponse(response);
        return new ResponseEntity<>(complimentListResponse, HttpStatus.OK);
    }

    @GetMapping("/challenge")
    public ResponseEntity<?> getChallengeComplimentList(){
        List<Compliment> challengeComplimentList = complimentService.getChallengeComplimentList();
        List<ComplimentResponse> response = challengeComplimentList.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        ComplimentListResponse complimentListResponse = new ComplimentListResponse(response);
        return new ResponseEntity<>(complimentListResponse, HttpStatus.OK);
    }

    @GetMapping("/preview")
    public ResponseEntity<?> getComplimentPreview(){
        List<Compliment> complimentPreview = complimentService.getComplimentPreview();
        List<ComplimentResponse> response = complimentPreview.stream()
                .map(compliment ->
                        {
                            Member member = memberService.getMemberByMemberId(compliment.getReceiverId());
                            return ComplimentResponse.builder()
                                    .complimentId(compliment.getId())
                                    .category(compliment.getCategory())
                                    .content(compliment.getContent())
                                    .name(member.getName())
                                    .companyName(member.getCompany().getName())
                                    .build();
                        }
                ).collect(Collectors.toList());
        ComplimentListResponse complimentListResponse = new ComplimentListResponse(response);
        return new ResponseEntity<>(complimentListResponse, HttpStatus.OK);
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
        ComplimentRankingListResponse complimentRankingListResponse = new ComplimentRankingListResponse(rankingResponse);
        return new ResponseEntity<>(complimentRankingListResponse, HttpStatus.OK);
    }

    // 오늘의 칭찬 주제 띄워주기
    @GetMapping("/challenge/theme")
    public TodayChallengeDto getTodayChallenge() throws IOException {
        return complimentService.getTodayChallenge();
    }
}
