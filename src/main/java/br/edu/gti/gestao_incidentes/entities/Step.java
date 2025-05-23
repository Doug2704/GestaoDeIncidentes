package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

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

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "action_plan_id")
    private ActionPlan actionPlan;

    //TODO alterar para actions
    @OneToMany(mappedBy = "step", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Action> actions;

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