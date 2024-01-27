package com.ssginc.secretgarden.domain.member.repository;

import com.ssginc.secretgarden.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findByBlossomId(String blossomId);

    Optional<Member> findByBlossomIdAndPassword(String blossomId, String password);

}
