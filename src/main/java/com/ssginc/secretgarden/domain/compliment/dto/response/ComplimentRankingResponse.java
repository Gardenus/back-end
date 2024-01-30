package com.ssginc.secretgarden.domain.compliment.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplimentRankingResponse {
    private Long rank;
    private Integer memberId;
    private String name;
    private Integer companyId;
    private String companyName;
    private Long count;
}
