package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.service.AssetService;
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
@RequestMapping("api/v1/admin/assets")
@RequiredArgsConstructor
public class AssetController {
    private AssetService assetService;

    @PostMapping("/create")
    public ResponseEntity<?> createAsset(@RequestBody @Validated(OnCreate.class) AssetRequestDTO assetRequestDTO) {
        try {
            Asset savedasset = assetService.create(assetRequestDTO);
            URI local = URI.create("/" + savedasset.getId());
            return ResponseEntity.created(local).body(savedasset);

        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.ok(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Asset foundasset = assetService.findById(id);
            return ResponseEntity.ok(foundasset);

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Asset>> findAll() {
        List<Asset> assets = assetService.findAll();
        return ResponseEntity.ok(assets);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) AssetRequestDTO asset) {
        try {
            Asset updatedasset = assetService.update(id, asset);
            return ResponseEntity.ok(updatedasset);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        try {
            assetService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuário apagado");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
