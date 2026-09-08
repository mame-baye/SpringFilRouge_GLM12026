package com.groupeisi.HelloSpring.controllers;

import com.groupeisi.HelloSpring.entities.Entreprise;
import com.groupeisi.HelloSpring.services.EntrepriseService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/entreprises")
public class EntrepriseController {

    private final EntrepriseService entrepriseService;

    @Operation(
            summary = "Liste des entreprises",
            description = "Retourne la liste de toutes les entreprises enregistrées"
    )
    @GetMapping
    public List<Entreprise> getAllEntreprises() {
        log.info("Liste de toutes les entreprises");
        return entrepriseService.findAll();
    }

    @Operation(
            summary = "Voir une entreprise par sa raison sociale",
            description = "Retourne l'entreprise dont la raison sociale est spécifiée"
    )
    @GetMapping("/{raisonSociale}")
    public ResponseEntity<Entreprise> getEntreprise(@PathVariable String raisonSociale) {
        log.info("Recherche de l'entreprise avec la raison sociale : {}", raisonSociale);
        Optional<Entreprise> entrepriseBd = entrepriseService.findByRaisonSociale(raisonSociale);
        return entrepriseBd.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(
            summary = "Créer une nouvelle entreprise",
            description = "Enregistre une nouvelle entreprise dans la base de données"
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Entreprise create(@RequestBody Entreprise entreprise) {
        log.info("Création de l'entreprise : {}", entreprise.getRaisonSociale());
        Entreprise result = entrepriseService.create(entreprise);
        log.info("Entreprise créée avec succès : {}", result.getRaisonSociale());
        return result;
    }

    @Operation(
            summary = "Modifier une entreprise",
            description = "Met à jour les informations d'une entreprise spécifiée par sa raison sociale"
    )
    @PutMapping("/{raisonSociale}")
    public ResponseEntity<Entreprise> update(
            @PathVariable String raisonSociale,
            @RequestBody Entreprise entreprise) {
        log.info("Mise à jour de l'entreprise : {}", raisonSociale);
        entreprise.setRaisonSociale(raisonSociale);
        Entreprise result = entrepriseService.update(entreprise);
        log.info("Entreprise mise à jour avec succès : {}", result.getRaisonSociale());
        return ResponseEntity.ok(result);
    }

    @Operation(
            summary = "Supprimer une entreprise",
            description = "Supprime l'entreprise spécifiée par sa raison sociale"
    )
    @DeleteMapping("/{raisonSociale}")
    public ResponseEntity<Void> delete(@PathVariable String raisonSociale) {
        log.info("Suppression de l'entreprise avec la raison sociale : {}", raisonSociale);
        entrepriseService.delete(raisonSociale);
        return ResponseEntity.noContent().build();
    }
}
