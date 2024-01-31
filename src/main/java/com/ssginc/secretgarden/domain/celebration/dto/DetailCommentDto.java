package com.ssginc.secretgarden.domain.celebration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ssginc.secretgarden.domain.celebration.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailCommentDto {

    private Integer id;
    private String content;
    private String nickname;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
    private LocalDateTime createdAt;

    public static DetailCommentDto toDto(Comment comment){
        return DetailCommentDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getNickname())
                .createdAt(comment.getCreatedAt())
                .build();
    }
}
