package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "execution")
@Getter
@Setter
public class Execution {
    //TODO implementar logs

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_execution")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "requester_id")
    private User requester;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "validator_id")
    private User validator;

    //TODO verificar quais cascade types
    @ManyToOne(cascade = {CascadeType.PERSIST})
    @JoinColumn(nullable = false, name = "plan_id")
    private ActionPlan actionPlan;

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
