package ru.itis.Tyshenko.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Ad {

    private Long id;
    private String header;
    private String description;
    private String contact;
    private Long price;
    private Long user_id;
    private Long resume_id;
}
