package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;

@Builder
public class LoginResponse {
    private String blossomId;
    private String name;
    private String companyName;
}
