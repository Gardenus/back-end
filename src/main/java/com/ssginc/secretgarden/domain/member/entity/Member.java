package com.ssginc.secretgarden.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "member")
public class Member {
    @Id
    private Long id;
}
