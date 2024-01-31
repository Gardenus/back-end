package com.ssginc.secretgarden.domain.celebration.dto;

import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListDetailCelebrationDto {

    private Integer id;
    private String company;
    private String nickname;
    private Boolean isSecret;
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    private LocalDateTime createdAt;

    public static ListDetailCelebrationDto toDto(Celebration celebration){

        return ListDetailCelebrationDto.builder()
                .id(celebration.getId())
                .company(celebration.getMember().getCompany().getName())
                .nickname(celebration.getNickname())
                .isSecret(celebration.getIsSecret())
                .title(celebration.getTitle())
                .content(celebration.getContent())
                .category(celebration.getCategory())
                .createdAt(celebration.getCreatedAt())
                .imageUrl(celebration.getImageUrl())
                .build();
    }
}
