package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CelebrationResponse {
    private String category;
    private Integer celebrationId;
    private String title;
    private String nickname;
}
