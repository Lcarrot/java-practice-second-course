package ru.itis.Tyshenko.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    private String country;
    private String hashPassword;
    private Boolean gender;
    private String confirmCode;

    @OneToMany(mappedBy = "owner")
    private List<Ad> ads;

    @OneToMany
    private List<Resume> resumes;
}
