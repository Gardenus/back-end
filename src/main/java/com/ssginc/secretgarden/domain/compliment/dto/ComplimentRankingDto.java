package com.ssginc.secretgarden.domain.compliment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ComplimentRankingDto {
    private Integer memberId;
    private Long count;
}
