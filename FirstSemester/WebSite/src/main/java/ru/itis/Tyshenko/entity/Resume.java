package ru.itis.Tyshenko.entity;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Resume {

    private Long id;
    private String header;
    private String description;
    private String contact;
    private Long user_id;
}
