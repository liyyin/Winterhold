package com.Winterhold.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseTokenDTO {
    @Schema(description = "Username yang digunakan untuk aplikasi cient-side")
    private String username;
    @Schema(description = "Role yang di gunakan untuk aplikasi client-side")
    private String role;
    @Schema(description = "Token JWT untuk requester")
    private String token;
    private String message;

    public ResponseTokenDTO(String username, String role, String token) {
        this.username = username;
        this.role = role;
        this.token = token;
    }
}
