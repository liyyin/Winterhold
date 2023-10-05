package com.Winterhold.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DropdownListDTO {
    private Long integerValue;
    private String stringValue;
    private String text;

    public DropdownListDTO(Long integerValue, String text) {
        this.integerValue = integerValue;
        this.text = text;
    }
    public DropdownListDTO(Long integerValue, String namaDepan,String namaBelakang) {
        this.integerValue = integerValue;
        if (namaBelakang == null){
            this.text = namaDepan;
        }else {
            this.text = String.format("%s %s",namaDepan,namaBelakang);
        }
    }
    public DropdownListDTO(String stringValue, String namaDepan,String namaBelakang) {
        this.stringValue = stringValue;
        if (namaBelakang == null){
            this.text = namaDepan;
        }else {
            this.text = String.format("%s %s",namaDepan,namaBelakang);
        }
    }

    public DropdownListDTO(String stringValue, String text) {
        this.stringValue = stringValue;
        this.text = text;
    }
}