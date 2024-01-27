package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;

@Builder
public class LoginResponseDto {
    private String blossomId;
    private String name;
    private String companyName;
}
