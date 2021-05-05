package ru.itis.tyshenko.rest.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String refreshToken;
    private Date expiredTime;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_user", referencedColumnName = "id")
    private User user;
}
