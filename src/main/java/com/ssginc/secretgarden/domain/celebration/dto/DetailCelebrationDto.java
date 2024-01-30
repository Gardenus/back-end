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
    private Boolean isSecret;
    private String nickname;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;

    public static DetailCelebrationDto toDto(Celebration celebration){
        return DetailCelebrationDto.builder()
                .id(celebration.getId())
                .writer(celebration.getMember().getName())
                .company(celebration.getMember().getCompany().getName())
                .isSecret(celebration.getIsSecret())
                .title(celebration.getTitle())
                .nickname(celebration.getNickname())
                .content(celebration.getContent())
                .category(celebration.getCategory())
                .createdAt(celebration.getCreatedAt())
                .build();
    }
}
