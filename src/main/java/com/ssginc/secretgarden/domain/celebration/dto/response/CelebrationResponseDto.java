package com.ssginc.secretgarden.domain.celebration.dto.response;

import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CelebrationResponseDto {

    private Integer id;

    public static CelebrationResponseDto toDto(Celebration celebration){
        return CelebrationResponseDto.builder()
                .id(celebration.getId())
                .build();
    }
}
