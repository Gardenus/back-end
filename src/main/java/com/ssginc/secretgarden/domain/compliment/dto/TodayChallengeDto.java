package com.ssginc.secretgarden.domain.compliment.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodayChallengeDto {

    private String theme;
    private String category;
}
