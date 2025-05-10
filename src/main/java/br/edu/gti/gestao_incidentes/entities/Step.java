package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "step")
@Getter
@Setter
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_step")
    private Long id;

    @Column(nullable = false, name = "title")
    String title;

    @Column(nullable = false, name = "action")
    String action;

    //TODO implementar no ActionPlanService alteração da posição
    @Column(name = "position")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int position;

    //TODO após setado, apenas o admin poderá alterar
    @ManyToOne
    @JoinColumn(nullable = false, name = "responsible_area_id")
    private Area responsibleArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_status")
    private Status status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.NOT_STARTED;
        }
    }
}
//TODO implementar no ExecutionService dependência da conclusão etapa anterior