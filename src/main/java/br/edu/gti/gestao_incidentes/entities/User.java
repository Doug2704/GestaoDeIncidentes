package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Profile;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
//TODO implementar lógica de autenticação de usuário
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(nullable = false, name = "name")
    private String name;

    //used to login
    @Column(nullable = false, name = "username", unique = true)
    private String username;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "password")
    private String password;

    //TODO verificar relacionamentos ManyToOne nas demais entidades
    //TODO implementar area default
    @ManyToOne
    @JoinColumn(name = "actuation_area_id")
    private Area actuationArea;

    @Column(nullable = false, name = "profile")
    private Profile profile;

    @Column(nullable = false, name = "registration_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime registrationDate;

    @PrePersist
    public void prePersist() {
        if (profile == null) {
            profile = Profile.DEFAULT;
        }
        registrationDate = LocalDateTime.now();

    }
}
