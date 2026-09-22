package com.groupeisi.HelloSpring.init;

import com.groupeisi.HelloSpring.entities.Entreprise;
import com.groupeisi.HelloSpring.entities.Etudiant;
import com.groupeisi.HelloSpring.entities.Stage;
import com.groupeisi.HelloSpring.repositories.EntrepriseRepository;
import com.groupeisi.HelloSpring.repositories.EtudiantRepository;
import com.groupeisi.HelloSpring.repositories.StageRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Component
public class Demarrage implements CommandLineRunner {


    private final EtudiantRepository etudiantRepository;
    private final EntrepriseRepository entrepriseRepository;
    private final StageRepository stageRepository;


    // 100 prénoms africains : 80 sénégalais + 20 autres africains
    private String[] prenomsAfricains = {
            // 80 prénoms sénégalais
            "Amadou", "Mamadou", "Ibrahima", "Ousmane", "Cheikh",
            "Abdoulaye", "Modou", "Babacar", "Pape", "Serigne",
            "Moussa", "Samba", "Lamine", "Boubacar", "Alioune",
            "Malick", "Mansour", "Souleymane", "Daouda", "Tidiane",
            "Thierno", "Saliou", "Assane", "Abdou", "Matar",
            "Mor", "El Hadji", "Massamba", "Demba", "Balla",
            "Youssoupha", "Birame", "Makhtar", "Cheikhouna", "Ibra",
            "Fatou", "Awa", "Mariama", "Astou", "Aminata",
            "Mame", "Khady", "Ndeye", "Sokhna", "Rama",
            "Coumba", "Adama", "Bineta", "Diary", "Anta",
            "Rokhaya", "Ndèye", "Seynabou", "Sokhna", "Maguette",
            "Dieynaba", "Nabou", "Fama", "Mame Diarra", "Mame Fatou",
            "Mame Awa", "Mame Khady", "Mame Astou", "Mame Coumba",
            "Mame Mariama", "Mame Aminata", "Mame Sokhna", "Mame Anta",
            "Mame Bineta", "Mame Seynabou", "Aïssatou", "Binta",
            "Kiné", "Nafi", "Yacine", "Fari", "Sokhna",
            "Ndèye Fatou", "Ndèye Awa", "Ndèye Astou",

            // 20 autres prénoms africains
            "Kwame", "Kofi", "Chinua", "Chinedu", "Ngozi",
            "Amara", "Zuberi", "Jelani", "Amani", "Baraka",
            "Nia", "Zuri", "Thabo", "Lerato", "Sipho",
            "Nomsa", "Tendai", "Tariro", "Chipo", "Mpho"
    };

    // 50 noms de famille africains : 40 sénégalais + 10 autres africains
    String[] nomsFamilleAfricains = {
            // 40 noms de famille sénégalais
            "Diop", "Ndiaye", "Fall", "Sow", "Ba",
            "Diallo", "Gueye", "Faye", "Sarr", "Sy",
            "Seck", "Mbaye", "Diouf", "Thiam", "Cissé",
            "Kane", "Dieng", "Niang", "Ndour", "Wade",
            "Tall", "Bâ", "Camara", "Touré", "Dramé",
            "Gassama", "Daff", "Sagna", "Badiane", "Mané",
            "Sonko", "Baldé", "Diedhiou", "Beye", "Ndao",
            "Lo", "Samb", "Gningue", "Bodian", "Coly",

            // 10 autres noms de famille africains
            "Mensah", "Okafor", "Nwosu", "Adeyemi", "Oluwole",
            "Mbeki", "Mahlangu", "Dlamini", "Chirwa", "Mwangi"
    };



    @Override
    public void run(String... args) throws Exception {
        log.info("Demarrage"); //trace/debug/info/warn/error
        long nbEtudiants = etudiantRepository.count();
        log.info("il existe {} étudiant(s) en base", nbEtudiants);

        if (nbEtudiants == 0) {
            log.warn("aucun etudfiant en base, initialisation des etudiants");
            int nbNEwEtudiant = (int)(Math.random()*20)+400;
            log.warn("{} seront crees", nbNEwEtudiant);
            for (int i=0; i<nbNEwEtudiant; i++) {
                int idxPrenom = (int)(Math.random()*prenomsAfricains.length);
                log.trace("indice prenom {}", idxPrenom);
                String prenom = prenomsAfricains[idxPrenom];
                log.trace("prenom {}", prenom);

                int idxNom = (int)(Math.random()*nomsFamilleAfricains.length);
                log.trace("indice nom {}", idxNom);
                String nom = nomsFamilleAfricains[idxNom];
                log.trace("nom {}", nom);

                Etudiant etudiant= new Etudiant();
                etudiant.setNom(nom);
                etudiant.setPrenom(prenom);
                etudiant.setEmail(prenom.charAt(0)+nom+i+"@groupeisi.com");
                etudiant.setNumCarte("2026GL"+(i+1));
                etudiantRepository.save(etudiant);
            }

        }else{
            log.info("il ya desja des données en base (pas d'initialisation a faire)");
        }

        long nbEntreprises = entrepriseRepository.count();
        log.info("il existe {} entreprise(s) en base", nbEntreprises);

        if (nbEntreprises == 0) {
            log.warn("aucune entreprise en base, initialisation de 5 entreprises");

            Entreprise e1 = new Entreprise("Sonatel", "Télécommunications", "Boulevard de la République, Dakar", "contact@orange-sonatel.sn", "+221338391200");
            Entreprise e2 = new Entreprise("Wave Digital Finance", "Fintech & Mobile Money", "Almadies, Dakar", "support@wave.com", "+221338000000");
            Entreprise e3 = new Entreprise("Free Sénégal", "Télécommunications", "Route des Almadies, Dakar", "contact@free.sn", "+221328240000");
            Entreprise e4 = new Entreprise("Gainde 2000", "Technologies de l'Information", "Point E, Dakar", "info@gainde2000.sn", "+221338593888");
            Entreprise e5 = new Entreprise("Atos Sénégal", "Services Numériques & IT", "Cité Keur Gorgui, Dakar", "contact@atos.net", "+221338690000");

            entrepriseRepository.save(e1);
            entrepriseRepository.save(e2);
            entrepriseRepository.save(e3);
            entrepriseRepository.save(e4);
            entrepriseRepository.save(e5);

            log.info("5 entreprises enregistrées avec succès au démarrage");
        } else {
            log.info("il y a déjà des entreprises en base (pas d'initialisation à faire)");
        }

        long nbStages = stageRepository.count();
        log.info("il existe {} stage(s) en base", nbStages);

        if (nbStages == 0) {
            log.warn("aucun stage en base, initialisation de 3 stages");

            Stage s1 = new Stage("Développement d'une plateforme e-commerce en microservices", LocalDate.of(2026, 4, 1), LocalDate.of(2026, 9, 30), "En cours");
            Stage s2 = new Stage("Mise en place d'un pipeline CI/CD avec Docker et Kubernetes", LocalDate.of(2026, 5, 1), LocalDate.of(2026, 10, 31), "Validé");
            Stage s3 = new Stage("Conception d'une application mobile de transfert d'argent", LocalDate.of(2026, 3, 15), LocalDate.of(2026, 8, 15), "Terminé");

            stageRepository.save(s1);
            stageRepository.save(s2);
            stageRepository.save(s3);

            log.info("3 stages enregistrés avec succès au démarrage");
        } else {
            log.info("il y a déjà des stages en base (pas d'initialisation à faire)");
        }
    }
}
