package ru.practicum.ewmclient.model.category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@Builder
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Integer id;

    @NotBlank
    private String name;

}