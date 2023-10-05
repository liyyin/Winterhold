package com.Winterhold.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JsonResultDTO {
    private String message;
    private boolean success;
    private Object obj;

    public JsonResultDTO(boolean success,String message){
        this.message = message;
        this.success = success;
    }

    public JsonResultDTO(boolean success, Object obj) {
        this.success = success;
        this.obj = obj;
    }
}
