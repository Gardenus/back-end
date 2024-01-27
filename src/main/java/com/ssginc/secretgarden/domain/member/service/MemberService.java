package com.ssginc.secretgarden.domain.member.service;

import com.ssginc.secretgarden.domain.member.dto.request.SignupRequestDto;
import com.ssginc.secretgarden.domain.member.entity.Company;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.CompanyRepository;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    public Boolean login(String blossomId, String password){
        return memberRepository.findByBlossomIdAndPassword(blossomId, password).isPresent();
    }

    public void signup(SignupRequestDto signupRequestDto) {
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
