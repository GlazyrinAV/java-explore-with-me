package ru.practicum.ewmclient.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank
    @JsonProperty("name")
    private String name;

    @NotBlank
    @Email
    @JsonProperty("email")
    private String email;

    private String password;

    private String role;

    private BigDecimal mark;


}