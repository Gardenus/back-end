package com.ssginc.secretgarden.domain.compliment.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WriteComplimentRequest {

    @NotEmpty
    private Integer companyId;
    @NotEmpty
    private String name;
    @NotEmpty
    private String category;
    @NotEmpty
    private String content;
}
