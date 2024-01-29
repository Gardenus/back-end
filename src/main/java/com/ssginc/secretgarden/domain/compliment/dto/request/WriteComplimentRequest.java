package com.ssginc.secretgarden.domain.compliment.dto.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WriteComplimentRequest {

    @NotEmpty
    private Integer receiverId;
    @NotEmpty
    private String category;
    @NotEmpty
    private String content;
}
