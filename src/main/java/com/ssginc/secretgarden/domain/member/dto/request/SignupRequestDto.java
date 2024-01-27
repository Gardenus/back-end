package com.ssginc.secretgarden.domain.member.dto.request;

import com.ssginc.secretgarden.domain.member.entity.Company;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.time.LocalDate;

@Data
public class SignupRequestDto {

    @NotEmpty
    private String blossomId;
    @NotEmpty
    private String password;
    @NotEmpty
    private LocalDate birthDate;
    @NotEmpty
    private String name;
    @NotEmpty
    private Integer companyId;
}
