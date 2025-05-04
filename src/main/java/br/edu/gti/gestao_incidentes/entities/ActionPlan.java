package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Area;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "action_plans")
@Getter
@Setter
//TODO apenas membros da área podem alterar
public class ActionPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_action_plan")
    private Long id;

    @Column(nullable = false, name = "title")
    private String title;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "incident_id")
    private Incident incident;

    @OneToMany
    @JoinColumn(name = "plan_id")
    private List<Step> steps;

    @Column(nullable = false, name = "responsible_area")
    private Area responsibleArea;

    @Column(nullable = false, name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime creationDate = LocalDateTime.now();

}
