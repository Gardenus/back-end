package com.ssginc.secretgarden.domain.compliment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@AllArgsConstructor
@Data
public class ComplimentRankingListResponse {
    private List<?> rankingList;
}
