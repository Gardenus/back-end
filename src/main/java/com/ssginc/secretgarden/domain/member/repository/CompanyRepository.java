package com.ssginc.secretgarden.domain.member.repository;

import com.ssginc.secretgarden.domain.member.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
