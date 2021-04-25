package ru.itis.tyshenko.entity;

import lombok.*;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.validator.internal.util.stereotypes.Lazy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@Entity
@Table(name = "account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    private String country;
    private String hashPassword;
    private String gender;
    private String confirmCode;
    private Role role;
    private boolean confirmed = false;
    private boolean isBanned = false;

    @OneToMany(mappedBy = "owner")
    private List<Ad> ads;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Lazy
    private List<Resume> resumes;

    public enum Role {
        USER,
        ADMIN
    }

    public enum Permission {
        GET,
        POST
    }
}
