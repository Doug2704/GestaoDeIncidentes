package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Level;
import br.edu.gti.gestao_incidentes.enums.Area;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "incidents")
@Getter
@Setter
public class Incident {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incident")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    @Column(nullable = false, name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "affected_area_id")
    private Area affectedArea;

    //TODO implementar nivel urgencia e nivel impacto
    @Column(nullable = false, name = "nivel")
    private Level level;

}
