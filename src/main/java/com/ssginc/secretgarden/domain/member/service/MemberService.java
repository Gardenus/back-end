package com.ssginc.secretgarden.domain.member.service;

import com.ssginc.secretgarden.domain.member.dto.request.SignupRequest;
import com.ssginc.secretgarden.domain.member.entity.Company;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.CompanyRepository;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    public Member login(String blossomId, String password){
        Member member =  memberRepository.findByBlossomIdAndPassword(blossomId, password)
                        .orElseThrow(()->new RuntimeException("ID 또는 비밀번호가 잘못되었습니다."));
        return member;
    }

    public void signup(SignupRequest signupRequestDto) {
        Integer companyId = signupRequestDto.getCompanyId();
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("존재하지 않는 계열사 id입니다."));
        Member member = Member.builder()
                .blossomId(signupRequestDto.getBlossomId())
                .password(signupRequestDto.getPassword())
                .birthDate(signupRequestDto.getBirthDate())
                .name(signupRequestDto.getName())
                .company(company)
                .build();
        memberRepository.save(member);
    }

    public Boolean idCheck(String blossomId) {
        Member member = memberRepository.findByBlossomId(blossomId);
        return member==null;
    }

    public Member getMember(String blossomId){
        return memberRepository.findByBlossomId(blossomId);
    }
}
