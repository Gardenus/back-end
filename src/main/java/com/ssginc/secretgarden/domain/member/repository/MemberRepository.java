package com.ssginc.secretgarden.domain.member.repository;

import com.ssginc.secretgarden.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    public Member findByBlossomId(String blossomId);

    Optional<Member> findByBlossomIdAndPassword(String blossomId, String password);

    @Query("SELECT m FROM Member m WHERE FUNCTION('MONTH', m.birthDate) = :month AND FUNCTION('DAY', m.birthDate) = :day")
    List<Member> findByMonthAndDay(@Param("month") int month, @Param("day") int day);

}
