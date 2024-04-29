package com.example.clear.model.DTO;

//import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDtoRequest {

//    @NotNull
//    private Long id;

    @NotNull
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String name;

    @NotNull
    @Size(min = 2, message = "user name should have at least 2 characters")
    private String surname;

    @NotNull
    @Email(message = "Not valid email")
    private String email;

    @NotNull
    @Past(message="date of birth must be less than today")
    @DateTimeFormat( pattern="yyyy-MM-dd")
    private Date birthdate;


    private String phone;


    private String address;


}
