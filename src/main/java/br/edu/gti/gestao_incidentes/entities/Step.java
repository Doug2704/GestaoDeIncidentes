package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "step")
@Getter
@Setter

//TODO implementar tasks
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_step")
    private Long id;

    @Column(nullable = false, name = "title")
    String title;

    @ManyToOne
    @JoinColumn(name = "action_plan_id")
    private ActionPlan actionPlan;

    //TODO mudar para entidade task
    @Column(nullable = false, name = "action")
    String action;

    //TODO após setado, apenas o admin poderá alterar
    @ManyToOne
    @JoinColumn(nullable = false, name = "responsible_area_id")
    private Area responsibleArea;

    @Enumerated(EnumType.STRING)
    @Column(name = "step_status")
    private Status status;

    //TODO max step duration
    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.NOT_STARTED;
        }
    }
}