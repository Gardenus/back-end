package com.ssginc.secretgarden.domain.member.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class CelebrationListResponse {
    private List<?> list;
}
