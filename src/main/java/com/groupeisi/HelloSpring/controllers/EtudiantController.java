package com.groupeisi.HelloSpring.controllers;

import com.groupeisi.HelloSpring.entities.Etudiant;
import com.groupeisi.HelloSpring.services.EtudiantService;
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
@RequestMapping("/etudiants")
@Tag(name = "etudiants", description = "Gestion du CRUD des etudiants")
public class EtudiantController {

    private final EtudiantService etudiantService;

    @Operation(summary = "Lister tous les etudiants", description = "Retourne la liste complete de tous les etudiants enregistres")
    @ApiResponse(responseCode = "200", description = "Liste recuperee avec succes")
    @GetMapping
    public ResponseEntity<List<Etudiant>> getAllEtudiants() {
        log.info("Recuperation de la liste de tous les etudiants");
        List<Etudiant> etudiants = etudiantService.findAll();
        return ResponseEntity.ok(etudiants);
    }

    @Operation(summary = "Obtenir un etudiant par son numero de carte", description = "Retourne un etudiant specifique à partir de son numero de carte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "etudiant trouve"),
            @ApiResponse(responseCode = "404", description = "etudiant non trouve")
    })
    @GetMapping("/{numCarte}")
    public ResponseEntity<Etudiant> getEtudiant(@PathVariable String numCarte) {
        log.info("Recherche de l'etudiant avec numCarte : {}", numCarte);
        return etudiantService.findByNumCarte(numCarte)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("etudiant non trouve avec numCarte : {}", numCarte);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Creer un nouvel etudiant", description = "Enregistre un nouvel etudiant dans la base de donnees")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "etudiant cree avec succes")
    })
    @PostMapping
    public ResponseEntity<Etudiant> create(@RequestBody Etudiant etudiant) {
        log.info("Creation d'un nouvel etudiant : {}", etudiant.getNumCarte());
        Etudiant result = etudiantService.create(etudiant);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @Operation(summary = "Mettre à jour un etudiant", description = "Modifie les informations d'un etudiant existant")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "etudiant mis à jour avec succes"),
            @ApiResponse(responseCode = "404", description = "etudiant non trouve")
    })
    @PutMapping("/{numCarte}")
    public ResponseEntity<Etudiant> update(
            @PathVariable String numCarte,
            @RequestBody Etudiant etudiant) {
        log.info("Mise à jour de l'etudiant : {}", numCarte);
        return etudiantService.update(numCarte, etudiant)
                .map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("Impossible de modifier, etudiant introuvable : {}", numCarte);
                    return ResponseEntity.notFound().build();
                });
    }

    @Operation(summary = "Supprimer un etudiant", description = "Supprime un etudiant de la base de donnees via son numero de carte")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "etudiant supprime avec succes"),
            @ApiResponse(responseCode = "404", description = "etudiant non trouve")
    })
    @DeleteMapping("/{numCarte}")
    public ResponseEntity<Void> delete(@PathVariable String numCarte) {
        log.info("Suppression de l'etudiant avec numCarte : {}", numCarte);
        boolean deleted = etudiantService.delete(numCarte);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
