package Model;

import java.util.ArrayList;
import java.util.List;

public class Besoin {
    private int id;
    private String nom;
    private List<CompetenceParBesoin> listeCompetencesRequises = new ArrayList<>();
    private int nbEmployes;

    public Besoin(String nomB, int nb) {
        this.id = 0;
        this.nom = nomB;
        this.nbEmployes = nb;
    }

    // TODO: get liste competences requises/nb personnes devant avoir la compétence pour le besoin
    // TODO: methode toString() à redéfinir
}
