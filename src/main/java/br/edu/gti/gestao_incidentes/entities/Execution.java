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

    @ManyToOne
    @JoinColumn(nullable = false, name = "plan_id")
    private ActionPlan actionPlan;

    @Column(nullable = false, name = "opening_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime openingDate;

    @Column(name = "finish_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime finishDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "execution_status")
    private Status status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.EXECUTING;
        }
        this.openingDate = LocalDateTime.now();
    }

    public void setValidator(User validator) {
        if (validator != getRequester()) this.validator = validator;
    }
}