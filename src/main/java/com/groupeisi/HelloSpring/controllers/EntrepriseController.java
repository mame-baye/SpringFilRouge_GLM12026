package com.groupeisi.HelloSpring.controllers;

import com.groupeisi.HelloSpring.entities.Entreprise;
import com.groupeisi.HelloSpring.services.EntrepriseService;
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
@RequestMapping("/entreprises")
@Tag(name = "Entreprises", description = "Gestion du CRUD des entreprises partenaires")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    @Operation(summary = "Lister toutes les entreprises", description = "Retourne la liste de toutes les entreprises enregistrées")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping
    public ResponseEntity<List<Entreprise>> getAllEntreprises() {
        log.info("Récupération de la liste de toutes les entreprises");
        List<Entreprise> entreprises = entrepriseService.findAll();
        return ResponseEntity.ok(entreprises);
    }

    @Operation(summary = "Voir une entreprise par sa raison sociale", description = "Retourne l'entreprise dont la raison sociale est spécifiée")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise trouvée"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @GetMapping("/{raisonSociale}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable String raisonSociale) {
        log.info("Recherche de l'entreprise avec la raison sociale : {}", raisonSociale);
        return entrepriseService.findByRaisonSociale(raisonSociale)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Entreprise non trouvée : {}", raisonSociale);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Créer une nouvelle entreprise", description = "Enregistre une nouvelle entreprise dans la base de données")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Entreprise créée avec succès")
    })
    @PostMapping
    public ResponseEntity<Entreprise> create(@RequestBody Entreprise entreprise) {
        log.info("Création de l'entreprise : {}", entreprise.getRaisonSociale());
        Entreprise result = entrepriseService.create(entreprise);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Modifier une entreprise", description = "Met à jour les informations d'une entreprise spécifiée par sa raison sociale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Entreprise mise à jour avec succès"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @PutMapping("/{raisonSociale}")
    public ResponseEntity<Entreprise> update(
            @PathVariable String raisonSociale,
            @RequestBody Entreprise entreprise) {
        log.info("Mise à jour de l'entreprise : {}", raisonSociale);
        return entrepriseService.update(raisonSociale, entreprise)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Impossible de modifier, entreprise introuvable : {}", raisonSociale);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Supprimer une entreprise", description = "Supprime l'entreprise spécifiée par sa raison sociale")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entreprise supprimée avec succès"),
            @ApiResponse(responseCode = "404", description = "Entreprise non trouvée")
    })
    @DeleteMapping("/{raisonSociale}")
    public ResponseEntity<Void> delete(@PathVariable String raisonSociale) {
        log.info("Suppression de l'entreprise avec la raison sociale : {}", raisonSociale);
        boolean deleted = entrepriseService.delete(raisonSociale);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
