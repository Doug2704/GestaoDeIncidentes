package br.edu.gti.gestao_incidentes.controller;

import br.edu.gti.gestao_incidentes.dto.requests.AssetRequestDTO;
import br.edu.gti.gestao_incidentes.entities.Asset;
import br.edu.gti.gestao_incidentes.exceptions.NoRegisterException;
import br.edu.gti.gestao_incidentes.exceptions.UniqueFieldViolationException;
import br.edu.gti.gestao_incidentes.service.AssetService;
import br.edu.gti.gestao_incidentes.validation.OnCreate;
import br.edu.gti.gestao_incidentes.validation.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("api/v1/areas/{responsibleAreaId}/assets")
@RequiredArgsConstructor
public class AssetController {
    private final AssetService assetService;

    @PostMapping("/create")
    public ResponseEntity<?> createAsset(@PathVariable Long responsibleAreaId, @RequestBody @Validated(OnCreate.class) AssetRequestDTO assetRequestDTO) {
        try {
            Asset savedasset = assetService.create(responsibleAreaId, assetRequestDTO);
            URI local = URI.create("/" + savedasset.getId());
            return ResponseEntity.created(local).body(savedasset);

        } catch (UniqueFieldViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Asset foundasset = assetService.findById(id);
            return ResponseEntity.ok(foundasset);

        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<Asset>> findByAreaId(@PathVariable Long responsibleAreaId) {
        List<Asset> assets = assetService.findByAreaId(responsibleAreaId);
        return ResponseEntity.ok(assets);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateAsset(@PathVariable Long id, @RequestBody @Validated(OnUpdate.class) AssetRequestDTO asset) {
        try {
            Asset updatedasset = assetService.update(id, asset);
            return ResponseEntity.ok(updatedasset);
        } catch (UniqueFieldViolationException | NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteAsset(@PathVariable Long id) {
        try {
            assetService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Ativo excluído");
        } catch (NoRegisterException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
