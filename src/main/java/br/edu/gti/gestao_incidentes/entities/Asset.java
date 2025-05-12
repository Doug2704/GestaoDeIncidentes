package br.edu.gti.gestao_incidentes.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "asset")
@Getter
@Setter
public class Asset {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_asset")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @ManyToOne
    @JoinColumn(nullable = false, name = "area_id")
    private Area responsibleArea;

    @Column(nullable = false, name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime creationDate;

    @PrePersist
    public void prePersist() {
        this.creationDate = LocalDateTime.now();
    }
}
