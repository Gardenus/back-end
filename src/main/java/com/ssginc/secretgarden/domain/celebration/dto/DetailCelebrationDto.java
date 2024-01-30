package com.ssginc.secretgarden.domain.celebration.dto;

import com.ssginc.secretgarden.domain.celebration.dto.response.CelebrationResponseDto;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import com.ssginc.secretgarden.domain.member.entity.Member;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailCelebrationDto {
    private Integer id;
    private String writer;
    private String company;
    private String title;
    private String content;
    private String category;
    private Boolean isSecret;
    private LocalDateTime createdAt;

    public static DetailCelebrationDto toDto(Celebration celebration){
        return DetailCelebrationDto.builder()
                .id(celebration.getId())
                .writer(celebration.getMember().getName())
                .company(celebration.getMember().getCompany().getName())
                .title(celebration.getTitle())
                .content(celebration.getContent())
                .category(celebration.getCategory())
                .isSecret(celebration.getIsSecret())
                .createdAt(celebration.getCreatedAt())
                .build();
    }
}
