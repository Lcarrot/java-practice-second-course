package ru.itis.Tyshenko.dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class AdDTO {
    public Long id;
    public String header;
    public String description;
    public String contact;
    public String price;
    public String user_login;
    public Long user_id;
}
