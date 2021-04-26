package ru.itis.tyshenko.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AdDto {
    public String header;
    public String description;
    public String contact;
    public String price;
}
