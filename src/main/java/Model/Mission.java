package Model;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Mission extends ModifierCSV {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private String id;
    private String nom;
    private Need need;
    private int nbEmployes;
    private Date dateDebut;
    private long duree;
    private MissionState status;

    public Mission(String nomM) {
        this.nom = nomM;
        this.id = String.valueOf(countID.incrementAndGet());
    }

    public Mission(String nomM, int nbEmployes) {
        this.id = String.valueOf(countID.incrementAndGet());
        this.nom = nomM;
        this.need = new Need();
        this.nbEmployes = nbEmployes;
        this.status = MissionState.PREPARATION;
    }

    public static AtomicInteger getCountID() {
        return countID;
    }


    /***********
     * GETTERS *
     ***********/

    public String getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public Need getNeed() {
        return need;
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

    public void addCompetence(Competence c, int nbEmployes) {
        if (!this.need.contains(c)) {
            try {
                appendCompToMission(this.id, c);
                this.need.addCompetence(c, nbEmployes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /***********
     * METHODS *
     ***********/

    /**
     * Sauvegarder une nouvelle mission dans le fichier LISTE_MISSION
     *
     * @throws IOException
     */
    public void writeMissionCSV() throws IOException {
        String mission = id + ";" + nom + ";" + nbEmployes + ";" + dateDebut + ";" + duree + ";" + status;
        appendNewLine(FILE_LISTE_MISSION, mission);
    }


    // TODO: get liste competences requises pour la mission
    // TODO: actualiser status de la mission
    // TODO: methode toString() à redéfinir
}
