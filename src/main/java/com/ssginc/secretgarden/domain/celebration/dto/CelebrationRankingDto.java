package com.ssginc.secretgarden.domain.celebration.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebrationRankingDto {

    private Integer rank;
    private String company;
    private Long commentCount;
}
