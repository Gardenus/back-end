package com.ssginc.secretgarden.domain.celebration.dto;

import lombok.*;

import java.util.List;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ListResponseDto {

    private List<ListDetailCelebrationDto> list;
}
