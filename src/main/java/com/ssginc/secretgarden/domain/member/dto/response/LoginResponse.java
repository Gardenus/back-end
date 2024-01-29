package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class LoginResponse {
    private Integer memberId;
    private String blossomId;
    private Integer companyId;
    private String companyName;
    private String name;
    private String token;
}
