package br.edu.gti.gestao_incidentes.entities;

import br.edu.gti.gestao_incidentes.enums.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long adminId;

    @Column(nullable = false, name = "nome")
    private String name;

    @Column(nullable = false, unique = true, name = "email")
    private String email;

    @Column(nullable = false, name = "senha")
    private String password;

    @Column(nullable = false, name = "data_registro")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime registrationDate = LocalDateTime.now();

    @Column(nullable = false, name = "role")
    private Role role;

    protected void setRole(Role role) {
        this.role = role;
    }
}
