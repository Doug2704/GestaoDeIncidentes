package br.edu.gti.gestao_incidentes.service;

import br.edu.gti.gestao_incidentes.dto.mappers.SimpleEntityMapper;
import br.edu.gti.gestao_incidentes.dto.responses.SimpleEntityDTO;
import br.edu.gti.gestao_incidentes.entities.Action;
import br.edu.gti.gestao_incidentes.entities.ActionPlan;
import br.edu.gti.gestao_incidentes.entities.user.User;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.repository.AuditLogRepository;
import br.edu.gti.gestao_incidentes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import br.edu.gti.gestao_incidentes.entities.AuditLog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {
    private final AuditLogRepository auditLogRepository;
    private final UserRepository userRepository;
    private final SimpleEntityMapper simpleEntityMapper;

    public void log(Long userId, String action, Object object) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoRegisterException(userId));
        SimpleEntityDTO entity = simpleEntityMapper.toDto(object);

        AuditLog log = new AuditLog();
        log.setUserProfile(user.getProfile().toString());
        log.setUserName(user.getName());
        log.setUserId(userId);
        log.setAction(action);
        log.setEntityType(entity.type());
        log.setEntityName(entity.name());
        log.setEntityId(entity.id());
        log.setActionDate(LocalDateTime.now());

        auditLogRepository.save(log);
    }

    public List<String> getLogs() {
        List<AuditLog> logs = auditLogRepository.findAll();
        List<String> logsString = new ArrayList<>();

        for (AuditLog log : logs) {
            logsString.add(toString(log));
        }
        return logsString;
    }


    public String toString(AuditLog log) {
        return log.getUserProfile() + " " + log.getUserName() + " "
                + log.getAction() + " " + log.getEntityType() + " "
                + log.getEntityName() + " às " + log.getActionDate();
    }
}