package com.groupeisi.HelloSpring.services;

import com.groupeisi.HelloSpring.entities.Entreprise;
import com.groupeisi.HelloSpring.repositories.EntrepriseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class EntrepriseService {

    private final EntrepriseRepository entrepriseRepository;

    // Lister toutes les entreprises
    public List<Entreprise> findAll() {
        return entrepriseRepository.findAll();
    }

    // Trouver une entreprise par sa raison sociale
    public Optional<Entreprise> findByRaisonSociale(String raisonSociale) {
        return entrepriseRepository.findById(raisonSociale);
    }

    // Créer une nouvelle entreprise
    public Entreprise create(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    // Mettre à jour une entreprise existante
    public Optional<Entreprise> update(String raisonSociale, Entreprise entreprise) {
        if (!entrepriseRepository.existsById(raisonSociale)) {
            return Optional.empty();
        }
        entreprise.setRaisonSociale(raisonSociale);
        return Optional.of(entrepriseRepository.save(entreprise));
    }

    // Supprimer une entreprise par sa raison sociale
    public boolean delete(String raisonSociale) {
        if (entrepriseRepository.existsById(raisonSociale)) {
            entrepriseRepository.deleteById(raisonSociale);
            return true;
        }
        return false;
    }

    // Vérifier si une entreprise existe
    public boolean existsByRaisonSociale(String raisonSociale) {
        return entrepriseRepository.existsById(raisonSociale);
    }
}
