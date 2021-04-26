package ru.itis.tyshenko.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Builder
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    private String country;
    private String hashPassword;
    private String gender;
    private String confirmCode;
    private boolean confirmed = false;

    @OneToMany(mappedBy = "owner")
    private List<Ad> ads;

    @OneToMany(mappedBy = "owner")
    private List<Resume> resumes;
}
