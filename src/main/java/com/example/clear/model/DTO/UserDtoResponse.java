package com.example.clear.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDtoResponse {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String surname;
    private Date birthdate;
    private String address;

    public void setStringBirthdate(String dateline) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", new Locale("ru"));
        this.birthdate = format.parse(dateline);
    }
}
