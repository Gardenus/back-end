package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
public class MemberResponse {
    private Integer memberId;
    private String name;
    private Integer companyId;
    private String companyName;
}
