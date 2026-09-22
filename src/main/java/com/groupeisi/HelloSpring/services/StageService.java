package com.groupeisi.HelloSpring.services;

import com.groupeisi.HelloSpring.entities.Stage;
import com.groupeisi.HelloSpring.repositories.StageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StageService {

    private final StageRepository stageRepository;

    // Lister tous les stages
    public List<Stage> findAll() {
        return stageRepository.findAll();
    }

    // Trouver un stage par son identifiant
    public Optional<Stage> findById(Long id) {
        return stageRepository.findById(id);
    }

    // Créer un nouveau stage
    public Stage create(Stage stage) {
        return stageRepository.save(stage);
    }

    // Mettre à jour un stage existant
    public Optional<Stage> update(Long id, Stage stage) {
        if (!stageRepository.existsById(id)) {
            return Optional.empty();
        }
        stage.setId(id);
        return Optional.of(stageRepository.save(stage));
    }

    // Supprimer un stage par son identifiant
    public boolean delete(Long id) {
        if (stageRepository.existsById(id)) {
            stageRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Vérifier si un stage existe
    public boolean existsById(Long id) {
        return stageRepository.existsById(id);
    }
}
