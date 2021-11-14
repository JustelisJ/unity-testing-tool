package com.endregas.warriors.unitytesting.model.dto;

import lombok.AllArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
public class RecentVideoDTO {

    @NotBlank(message = "Name of a video cannot be blank")
    private String name;
    @NotBlank(message = "Source of the video cannot be blank")
    private String src;

}

