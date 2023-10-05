package com.Winterhold.dto.author;

import com.Winterhold.Helper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AuthorDTO {
    private Long id;
    private String fullName;
    private String birthDate;
    private Long age;
    private LocalDate deceasedDate;
    private String stringDeceasedDate;
    private String education;
    private String summary;

    public AuthorDTO(Long id,String title,String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education) {
        this.id = id;
        if (lastName == null){
            this.fullName = String.format("%s. %s",title,firstName);
        }else {
            this.fullName = String.format("%s. %s %s",title,firstName,lastName);
        }
        this.birthDate = Helper.formatTanggal(birthDate,"dd/MM/yyyy");
        if (deceasedDate == null){
            this.age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        }else {
            this.age = ChronoUnit.YEARS.between(birthDate, deceasedDate);
        }
        this.deceasedDate = deceasedDate;
        this.education = education;
    }
    public AuthorDTO(Long id,String title,String firstName, String lastName, LocalDate birthDate, LocalDate deceasedDate, String education,String summary) {
        this.id = id;
        if (lastName == null){
            this.fullName = String.format("%s. %s",title,firstName);
        }else {
            this.fullName = String.format("%s. %s %s",title,firstName,lastName);
        }
        this.birthDate = Helper.formatTanggal(birthDate,"dd MMMM yyyy");
        if (deceasedDate == null){
            this.age = ChronoUnit.YEARS.between(birthDate, LocalDate.now());
        }else {
            this.age = ChronoUnit.YEARS.between(birthDate, deceasedDate);
        }
        this.deceasedDate = deceasedDate;
        if (this.deceasedDate == null){
            this.stringDeceasedDate = "-";
        }else {
            this.stringDeceasedDate = Helper.formatTanggal(deceasedDate,"dd MMMM yyyy");
        }
        this.education = education;
        this.summary = summary;
    }
}
