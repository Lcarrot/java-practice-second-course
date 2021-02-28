package ru.itis.Tyshenko.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@Table
@NoArgsConstructor
@Data
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private String contact;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;


}
