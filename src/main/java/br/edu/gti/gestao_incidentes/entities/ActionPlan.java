package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Level;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "action_plan")
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

    @OneToMany(mappedBy = "actionPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Step> steps;

    //TODO após setado, apenas o admin poderá alterar
    @ManyToOne
    @JoinColumn(nullable = false, name = "responsible_area_id")
    private Area responsibleArea;

    //TODO apenas funcionario que criou e admin poderão alterar
    @ManyToMany
    @JoinTable(
            name = "plan_affected_areas",
            joinColumns = @JoinColumn(name = "plan_id"),
            inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<Area> affectedAreas;

    @Column(nullable = false, name = "incident_description")
    private String incidentDescription;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "urgency_level")
    private Level urgencyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "impact_level")
    private Level impactLevel;

    @Column(nullable = false, name = "creation_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime creationDate;


    @PrePersist
    public void prePersist(){
        this.creationDate=LocalDateTime.now();
    }
}
