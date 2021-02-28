package ru.itis.Tyshenko.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Entity
@Table
@NoArgsConstructor
@Data
public class Ad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String header;
    private String description;
    private String contact;
    private Long price;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User user;
}
