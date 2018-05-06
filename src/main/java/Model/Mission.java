package Model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Mission extends ModifierCSV {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private String id;
    private String name;
    private Need need;
    private int nbEmployes;
    private Date dateDebut;
    private int duration;
    private MissionState status;

    public Mission(String nomM, Company company) {
        MissionMgt missionMgt = new MissionMgt();

        this.id = String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id, company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionState.PREPARATION;
    }

    public Mission(String nomM, int nbEmployes, Company company) {
        MissionMgt missionMgt = new MissionMgt();
        this.id = String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id, company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbEmployes = nbEmployes;
        this.status = MissionState.PREPARATION;
    }

    public Mission(String id, String name, String nbEmployes, String dateDebut, String duration, String status, Company company) {
        MissionMgt missionMgt = new MissionMgt();
        this.id = id;
        this.name = name;
        try {
            this.need = new Need(id, company);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbEmployes = Integer.valueOf(nbEmployes);
        if (!dateDebut.equals("null")) {
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            try {
                this.dateDebut = formatter.parse(dateDebut);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        this.duration = Integer.valueOf(duration);
        this.status = MissionState.valueOf(status);
    }

    public static AtomicInteger getCountID() {
        return countID;
    }


    //***********//
    //  GETTERS  //
    //***********//


    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public long getDuration() {
        return duration;
    }

    public MissionState getStatus() {
        return status;
    }

    public ArrayList<Employee> getTeam() {
        return this.need.getTeam();
    }


    //***********//
    //  SETTERS  //
    //***********//

    public void setName(String name) {
        this.name = name;
    }

    public void setNbEmployes(int nbEmployes) {
        this.nbEmployes = nbEmployes;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setStatus(MissionState status) {
        this.status = status;
    }


    //***********//
    //  METHODS  //
    //***********//


    public void addCompetence(Competence c, int nbEmployes) {
        MissionMgt missionMgt = new MissionMgt();
        if (!this.need.contains(c)) {
            try {
                missionMgt.appendCompToMission(this.id, c, nbEmployes);
                this.need.addCompetence(c, nbEmployes);
            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public void addEmployee(Competence c, Employee e) {
        MissionMgt missionMgt = new MissionMgt();
        if (!this.need.contains(c)) {
            try {
                missionMgt.appendEmpToMission(this.id, c, e);
                this.need.addEmployee(c, e);
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
                e1.printStackTrace();
            }
        }
    }

    /**
     * Sauvegarder une nouvelle mission dans le fichier LISTE_MISSION
     *
     * @throws IOException
     */
    public void writeMissionCSV() throws IOException {
        String mission = id + ";" + name + ";" + nbEmployes + ";" + dateDebut + ";" + duration + ";" + status.name();
        appendNewLine(FILE_LISTE_MISSION, mission);
    }

}
