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

    public List<Entreprise> findAll() {
        return entrepriseRepository.findAll();
    }

    public Optional<Entreprise> findByRaisonSociale(String raisonSociale) {
        return entrepriseRepository.findById(raisonSociale);
    }

    public Entreprise create(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    public Entreprise update(Entreprise entreprise) {
        return entrepriseRepository.save(entreprise);
    }

    public void delete(String raisonSociale) {
        entrepriseRepository.deleteById(raisonSociale);
    }

    public boolean existsByRaisonSociale(String raisonSociale) {
        return entrepriseRepository.existsById(raisonSociale);
    }
}
