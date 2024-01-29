package com.ssginc.secretgarden.domain.member.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty
    private String blossomId;
    @NotEmpty
    private String password;
}
