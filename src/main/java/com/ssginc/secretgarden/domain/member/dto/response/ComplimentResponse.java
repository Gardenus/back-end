package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ComplimentResponse {
    private Integer complimentId;
    private String category;
    private String theme;
    private String name;
    private String companyName;
    private String content;
    private String imageUrl;
}
