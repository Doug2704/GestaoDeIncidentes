package br.edu.gti.gestao_incidentes.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_task")
    private Long id;

    @Column(name = "title")
    private String action;

    @ManyToOne
    @JoinColumn(name = "step_id")
    private Step step;

    private boolean isDone;

    @PrePersist
    public void prePersist() {
        this.isDone = false;
    }
}
