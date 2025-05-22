package br.edu.gti.gestao_incidentes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(unique = true, nullable = false, length = 1000)
    private String token;

    @Column(nullable = false)
    private LocalDateTime expirationDate;

    @PrePersist
    public void prePersist() {
        this.expirationDate = LocalDateTime.now();
    }
}
