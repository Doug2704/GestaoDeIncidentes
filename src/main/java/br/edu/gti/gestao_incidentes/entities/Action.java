package br.edu.gti.gestao_incidentes.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Action {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action")
    private Long id;

    @Column(name = "title", unique = true)
    private String title;

    @ManyToOne
    @JoinColumn(name = "step_id")
    @JsonIgnore
    private Step step;

    private boolean isDone;

    @PrePersist
    public void prePersist() {
        this.isDone = false;
    }
}
