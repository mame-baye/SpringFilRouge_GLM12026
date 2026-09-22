package com.groupeisi.HelloSpring.services;

import com.groupeisi.HelloSpring.entities.Etudiant;
import com.groupeisi.HelloSpring.repositories.EtudiantRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EtudiantService {

    private final EtudiantRepository etudiantRepository;

    // Lister tous les étudiants
    public List<Etudiant> findAll() {
        return etudiantRepository.findAll();
    }

    // Trouver un étudiant par son numéro de carte
    public Optional<Etudiant> findByNumCarte(String numCarte) {
        return etudiantRepository.findById(numCarte);
    }

    // Créer un nouvel étudiant
    public Etudiant create(Etudiant etudiant) {
        return etudiantRepository.save(etudiant);
    }

    // Mettre à jour un étudiant existant
    public Optional<Etudiant> update(String numCarte, Etudiant etudiant) {
        if (!etudiantRepository.existsById(numCarte)) {
            return Optional.empty();
        }
        etudiant.setNumCarte(numCarte);
        return Optional.of(etudiantRepository.save(etudiant));
    }

    // Supprimer un étudiant par son numéro de carte
    public boolean delete(String numCarte) {
        if (etudiantRepository.existsById(numCarte)) {
            etudiantRepository.deleteById(numCarte);
            return true;
        }
        return false;
    }

    // Vérifier l'existence d'un étudiant
    public boolean existsByNumCarte(String numCarte) {
        return etudiantRepository.existsById(numCarte);
    }
}
