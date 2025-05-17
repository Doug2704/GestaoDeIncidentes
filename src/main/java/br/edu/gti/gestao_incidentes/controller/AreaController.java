package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.AreaRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Area;
import br.edu.gti.gestao_incidentes.service.AreaService;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/areas")
@RequiredArgsConstructor
public class AreaController {
    private AreaService areaService;

    @PostMapping("/create")
    public ResponseEntity<?> createArea(@RequestBody @Validated(OnCreate.class) AreaRequestDTO AreaRequestDTO) {
        try {
            Area savedArea = areaService.create(AreaRequestDTO);
            URI local = URI.create("/" + savedArea.getId());
            return ResponseEntity.created(local).body(savedArea);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Area foundArea = areaService.findById(id);
            return ResponseEntity.ok(foundArea);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Area>> findAll() {
        List<Area> areas = areaService.findAll();
        return ResponseEntity.ok(areas);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateArea(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) AreaRequestDTO area) {
        try {
            Area updatedarea = areaService.update(id, area);
            return ResponseEntity.ok(updatedarea);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteArea(@PathVariable Long id) {
        try {
            areaService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Área excluída");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
