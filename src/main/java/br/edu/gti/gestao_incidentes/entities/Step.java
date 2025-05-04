package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "step")
@Getter
@Setter
public class Step {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_step")
    Long id;

    @Column(name = "title")
    String title;

    @Column(name = "action")
    String action;

    @Column(name = "position")
    int position;

    @Column(nullable = false, name = "openning_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime openingDate = LocalDateTime.now();

    @Column(name = "finish_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime finishDate;

    @Column(name = "step_max_duration")
    LocalDateTime stepMaxDuration;

    @Column(name = "execution_status")
    private Status status;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = Status.WAITING;
        }
    }
}
