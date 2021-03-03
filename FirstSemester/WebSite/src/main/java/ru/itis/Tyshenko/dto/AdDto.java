package ru.itis.Tyshenko.dto;

import lombok.*;

@Builder
@Data
public class AdDto {
    public String header;
    public String description;
    public String contact;
    public String price;
}
