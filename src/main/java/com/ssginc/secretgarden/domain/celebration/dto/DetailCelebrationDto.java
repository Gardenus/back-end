package com.ssginc.secretgarden.domain.celebration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssginc.secretgarden.domain.celebration.entity.Celebration;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailCelebrationDto {
    private Integer id;
    private String company;
    private String nickname;
    private Boolean isSecret;
    private String title;
    private String content;
    private String category;
    private String imageUrl;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    private List<DetailCommentDto> commentList;

    public static DetailCelebrationDto toDto(Celebration celebration){

        List<DetailCommentDto> commentDtoList = celebration.getCommentList().stream()
                .map(DetailCommentDto::toDto)
                .toList();

        return DetailCelebrationDto.builder()
                .id(celebration.getId())
                .company(celebration.getMember().getCompany().getName())
                .nickname(celebration.getNickname())
                .isSecret(celebration.getIsSecret())
                .title(celebration.getTitle())
                .content(celebration.getContent())
                .category(celebration.getCategory())
                .createdAt(celebration.getCreatedAt())
                .imageUrl(celebration.getImageUrl())
                .commentList(commentDtoList)
                .build();
    }
}
