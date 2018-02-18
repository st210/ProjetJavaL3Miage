package Model;

import java.util.Date;
import java.util.List;

public class Employe {
    private int id;
    private String nom;
    private String prenom;
    private Date dateEntreeEntreprise;
    private List<Competence> competences;

    public Employe(String nomE, String prenomE, Date entree) {
        this.id = 0;
        this.nom = nomE;
        this.prenom = prenomE;
        this.dateEntreeEntreprise = entree;
        this.competences = null;
    }

    // TODO: get/set liste competences d'un employe
    // TODO: gestion de la date d'entree dans l'entp d'un employe
    // TODO: methode toString() à redéfinir
}
