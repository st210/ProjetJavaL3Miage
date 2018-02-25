package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Mission {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;
    private String nom;
    private List<Competence> listeCompetences = new ArrayList();
    private int nbEmployes;
    private Date dateDebut;
    private long duree;
    private Statut statut;
    private boolean anomalie;

    public Mission(String nomC) {
        this.nom = nomC;
        this.anomalie = false;
        this.id = countID.incrementAndGet();
    }

    public Mission(String nomC, List<Competence> listeC, int nb) {
        this.id = 0;
        this.nom = nomC;
        this.listeCompetences = listeC;
        this.nbEmployes = nb;
        this.anomalie = false;
    }


    // TODO: get liste competences requises pour la mission
    // TODO: actualiser statut de la mission
    // TODO: methode toString() à redéfinir
}
