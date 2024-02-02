package com.ssginc.secretgarden.domain.compliment.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateComplimentDto {

    private Integer id;
    private String answer;
}
