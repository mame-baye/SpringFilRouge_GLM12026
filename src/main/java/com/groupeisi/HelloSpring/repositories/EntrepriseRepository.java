package com.groupeisi.HelloSpring.repositories;

import com.groupeisi.HelloSpring.entities.Entreprise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntrepriseRepository extends JpaRepository<Entreprise, String> {
}
