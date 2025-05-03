package br.edu.gti.gestao_incidentes.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "executions")
@Getter
@Setter
public class Execution {
    //TODO implementar logs
    //TODO criar entidades Areas(id, nome), Ativos(id, nome, area) relacionar tabelas
    // ver com Josué se ativo pode ser String

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

    @Column(nullable = false, name = "openning_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime openingDate = LocalDateTime.now();

    @Column(name = "finish_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime finishDate;

    @Column(nullable = false, name = "plan_max_duration")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private Duration planMaxDuration;
}
