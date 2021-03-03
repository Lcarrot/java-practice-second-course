package ru.itis.Tyshenko.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class ResumeDto {

    public String header;
    public String description;
    public String contact;
}
