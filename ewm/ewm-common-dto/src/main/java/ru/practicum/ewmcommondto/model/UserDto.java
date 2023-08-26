package ru.practicum.ewmcommondto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Integer id;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

}