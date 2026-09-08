package com.groupeisi.HelloSpring.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Entreprise {
    @Id
    @Column(unique = true,  nullable = false, length = 150)
    private String raisonSociale;

    @Column(length = 150)
    private String secteurActivite;

    @Column(length = 200)
    private String adresse;

    @Column(length = 70,  nullable = false)
    private String email;

    @Column(length = 30,  nullable = false)
    private String telephone;
}