package com.ssginc.secretgarden.domain.compliment.dto.response;

import lombok.Data;

@Data
public class ComplimentRankingResponse {
    private Long rank;
    private Integer memberId;
    private String name;
    private Integer companyId;
    private String companyName;
    private Long count;
}
