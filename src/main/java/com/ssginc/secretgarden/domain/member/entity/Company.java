package com.ssginc.secretgarden.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "company")
public class Company {
    @Id
    private Integer id;

    private String name;
}
