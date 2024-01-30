package com.ssginc.secretgarden.domain.celebration.dto.response;

import com.ssginc.secretgarden.domain.celebration.entity.Comment;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {

    private Integer id;

    public static CommentResponseDto toDto(Comment comment){
        return CommentResponseDto.builder()
                .id(comment.getId())
                .build();
    }
}
