package com.ssginc.secretgarden.domain.celebration.dto;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateCommentDto {

    private Integer id;
    private String answer;
}
