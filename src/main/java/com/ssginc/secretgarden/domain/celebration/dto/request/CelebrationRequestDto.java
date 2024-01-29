package com.ssginc.secretgarden.domain.celebration.dto.request;

import lombok.*;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebrationRequestDto {

    private String title;
    private String content;
    private Boolean isSecret;
}
