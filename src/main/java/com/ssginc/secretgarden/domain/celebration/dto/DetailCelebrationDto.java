package com.ssginc.secretgarden.domain.celebration.dto;

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
    private String writer;
    private String company;
    private Boolean isSecret;
    private String nickname;
    private String title;
    private String content;
    private String category;
    private LocalDateTime createdAt;

    private List<DetailCommentDto> commentList;

    public static DetailCelebrationDto toDto(Celebration celebration){

        List<DetailCommentDto> commentDtoList = celebration.getCommentList().stream()
                .map(DetailCommentDto::toDto)
                .toList();

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
                .commentList(commentDtoList)
                .build();
    }
}
