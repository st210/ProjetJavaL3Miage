package Model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Mission {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;
    private String nom;
    private ArrayList<Competence> competencesMission = new ArrayList<>();
    private int nbEmployes;
    private Date dateDebut;
    private long duree;
    private MissionState status;

    public Mission(String nomC) {
        this.nom = nomC;
        this.id = countID.incrementAndGet();
    }

    public Mission(String nomC, ArrayList<Competence> listeC, int nb) {
        this.id = 0;
        this.nom = nomC;
        this.competencesMission = listeC;
        this.nbEmployes = nb;
    }

    public static AtomicInteger getCountID() {
        return countID;
    }


    /***********
     * GETTERS *
     ***********/

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public List<Competence> getCompetencesMission() {
        return competencesMission;
    }

    public int getNbEmployes() {
        return nbEmployes;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public long getDuree() {
        return duree;
    }

    public MissionState getStatus() {
        return status;
    }


    /***********
     * SETTERS *
     ***********/

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCompetencesMission(ArrayList<Competence> competencesMission) {
        this.competencesMission = competencesMission;
    }

    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDuree(long duree) {
        this.duree = duree;
    }

    public void setStatus(MissionState status) {
        this.status = status;
    }

    // TODO: get liste competences requises pour la mission
    // TODO: actualiser status de la mission
    // TODO: methode toString() à redéfinir
}
