package com.ssginc.secretgarden.domain.celebration.dto;

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
