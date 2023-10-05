package com.Winterhold.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter@Setter
public class RequestTokenDTO {
    @Schema(description = "USername maximum 20 char")
    private String username;
    @Schema(description = "Password maximum 20 char")
    private String password;
    @Schema(description = "Username,email atau topic dari requester")
    private String subject;
    @Schema(description = "Secret Key dari API")
    private String secretKey;
    @Schema(description = "Pengguna API")
    private String audience;
}
