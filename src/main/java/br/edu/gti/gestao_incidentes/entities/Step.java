package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

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

    @Column(name = "position")
    int position;

    @ManyToOne
    @JoinColumn(nullable = false, name = "action_plan_id")
    private ActionPlan actionPlan;

    //TODO após setado, apenas o admin poderá alterar
    @ManyToOne
    @JoinColumn(nullable = false, name = "responsible_area_id")
    private Area responsibleArea;

    @Column(nullable = false, name = "opening_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime openingDate;

    @Column(name = "finish_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime finishDate;

    //TODO implementar lógica para converter hora e minuto em minutos
    @Column(nullable = false, name = "plan_max_duration")
    private Long planMaxDuration; // Duração em minutos

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_status")
    private Status status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.WAITING;
        }
        this.openingDate = LocalDateTime.now();
    }
}
//TODO implementar na camada de serviço dependência da conclusão etapa anterior