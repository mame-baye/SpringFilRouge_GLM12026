package com.groupeisi.HelloSpring.controllers;

import com.groupeisi.HelloSpring.entities.Stage;
import com.groupeisi.HelloSpring.services.StageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/stages")
@Tag(name = "Stages", description = "Gestion du CRUD des stages")
public class StageController {

    private final StageService stageService;

    @Operation(summary = "Lister tous les stages", description = "Retourne la liste de tous les stages enregistrés")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping
    public ResponseEntity<List<Stage>> getAllStages() {
        log.info("Récupération de la liste de tous les stages");
        List<Stage> stages = stageService.findAll();
        return ResponseEntity.ok(stages);
    }

    @Operation(summary = "Obtenir un stage par son identifiant", description = "Retourne le stage correspondant à l'ID spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stage trouvé"),
            @ApiResponse(responseCode = "404", description = "Stage non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Stage> getStage(@PathVariable Long id) {
        log.info("Recherche du stage avec l'identifiant : {}", id);
        return stageService.findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Stage non trouvé avec l'identifiant : {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Créer un nouveau stage", description = "Enregistre un nouveau stage dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Stage créé avec succès")
    })
    @PostMapping
    public ResponseEntity<Stage> create(@RequestBody Stage stage) {
        log.info("Création d'un nouveau stage : {}", stage.getSujetDefinitif());
        Stage result = stageService.create(stage);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Modifier un stage", description = "Met à jour les informations d'un stage existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Stage mis à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Stage non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Stage> update(
            @PathVariable Long id,
            @RequestBody Stage stage) {
        log.info("Mise à jour du stage : {}", id);
        return stageService.update(id, stage)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Impossible de modifier, stage introuvable avec l'id : {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Supprimer un stage", description = "Supprime le stage correspondant à l'identifiant spécifié")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Stage supprimé avec succès"),
            @ApiResponse(responseCode = "404", description = "Stage non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("Suppression du stage avec l'id : {}", id);
        boolean deleted = stageService.delete(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
