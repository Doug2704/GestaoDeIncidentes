package br.edu.gti.gestao_incidentes.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@Getter
@Setter
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_profile")
    private String userProfile;

    @Column(nullable = false, name = "user_name")
    private String userName;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "action")
    private String action;

    @Column(nullable = false, name = "entity_type")
    private String entityType;

    @Column(nullable = false, name = "entity_name")
    private String entityName;

    @Column(nullable = false, name = "entity_id")
    private Long entityId;

    @Column(nullable = false, name = "registration_date")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    private LocalDateTime actionDate;

}
