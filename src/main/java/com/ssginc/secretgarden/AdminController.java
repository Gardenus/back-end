package com.ssginc.secretgarden;


import com.ssginc.secretgarden.domain.member.entity.Company;
import com.ssginc.secretgarden.domain.member.entity.Member;
import com.ssginc.secretgarden.domain.member.repository.CompanyRepository;
import com.ssginc.secretgarden.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final MemberRepository memberRepository;
    private final CompanyRepository companyRepository;

    @GetMapping("/admin/{memberId}")
    public String adminPage(@PathVariable("memberId") Integer memberId, Model model){

        if(memberId == 1){
            List<Member> members = memberRepository.findAll();
            List<Company> companies = companyRepository.findAll();
            model.addAttribute("members",members);
            model.addAttribute("companies",companies);
            return "adminPage";
        } else {
            return "redirect:/main";
        }
    }
}
