package Model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Mission extends ModifierCSV {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private String id;
    private String name;
    private Need need;
    private int nbEmployes;
    private Date dateDebut;
    private int duration;
    private MissionStatus status;

    public Mission(String nomM) {
        MissionMgt missionMgt = new MissionMgt();

        this.id = String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;
    }

    public Mission(String nomM, int nbEmployes) {
        MissionMgt missionMgt = new MissionMgt();
        this.id = String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbEmployes = nbEmployes;
        this.status = MissionStatus.PREPARATION;
    }

    public Mission(String name, int nbEmployes, Date dateDebut) {
        this.id = String.valueOf(countID.incrementAndGet());
        this.name = name;
        this.nbEmployes = nbEmployes;
        this.dateDebut = dateDebut;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;

    }

    public Mission(String name, int nbEmployes, Date dateDebut, int duration) {
        this.id = String.valueOf(countID.incrementAndGet());
        this.name = name;
        this.nbEmployes = nbEmployes;
        this.dateDebut = dateDebut;
        this.duration = duration;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;

    }

    public Mission(String id, String nomM) {
        this.id = id != null ? id : String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;
    }

    public Mission(String id, String nomM, int nbEmployes) {
        this.id = id != null ? id : String.valueOf(countID.incrementAndGet());
        this.name = nomM;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.nbEmployes = nbEmployes;
        this.status = MissionStatus.PREPARATION;
    }

    public Mission(String id, String name, int nbEmployes, Date dateDebut) {
        this.id = id != null ? id : String.valueOf(countID.incrementAndGet());
        this.name = name;
        this.nbEmployes = nbEmployes;
        this.dateDebut = dateDebut;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;

    }

    public Mission(String id, String name, int nbEmployes, Date dateDebut, int duration) {
        this.id = id != null ? id : String.valueOf(countID.incrementAndGet());
        this.name = name;
        this.nbEmployes = nbEmployes;
        this.dateDebut = dateDebut;
        this.duration = duration;
        try {
            this.need = new Need(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.status = MissionStatus.PREPARATION;
    }

    public Mission(String id, String name, String nbEmployes, String dateDebut, String duration, String status, Company company) {
        MissionMgt missionMgt = new MissionMgt();
        this.id = id;
        this.name = name;
        try {
            this.need = new Need(id);
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
        this.status = MissionStatus.valueOf(status);
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

    public int getDuration() {
        return duration;
    }

    public MissionStatus getStatus() {
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

    public void setStatus(MissionStatus status) {
        this.status = status;
    }


    //***********//
    //  METHODS  //
    //***********//

    /**
     * Determine le status de la mission
     */
    public void adjustStatus() {
        if (this.isScheduled()) {
            this.status = MissionStatus.SCHEDULED;
        } else if (isInProgress()) {
            this.status = MissionStatus.PROGRESS;
        } else if (isCompleted()) {
            this.status = MissionStatus.COMPLETED;
        }
    }

    /**
     * Determine si la mission est en cours ou non
     *
     * @return True si en cours, False sinon
     */
    private boolean isInProgress() {
        Date today = new Date();
        Calendar endDate = Calendar.getInstance();
        Calendar todayC = Calendar.getInstance();
        todayC.setTime(today);
        endDate.setTime(dateDebut);
        endDate.add(Calendar.DATE, duration);
        return getDateDebut().before(today) && endDate.after(todayC);

    }

    /**
     * Determine si la mission est programmée ou non
     *
     * @return True si programmée, False sinon
     */
    private boolean isScheduled() {
        AtomicBoolean isScheduled = new AtomicBoolean(true);
        Date today = new Date();
        this.getNeed().getCompetenceCurrent().forEach((competence, employees) -> {
            if (isScheduled.get() && (!this.getNeed().getCompetenceInit().containsKey(competence) || (this.getNeed().getCompetenceInit().get(competence) != employees.size()))) {
                isScheduled.set(false);
            }
        });
        return isScheduled.get() && getDateDebut().after(today) && getTeam().size() >= nbEmployes;
    }

    /**
     * Determine si la mission est terminée
     *
     * @return True si terminée, False sinon
     */
    private boolean isCompleted() {
        Date today = new Date();
        Calendar endDate = Calendar.getInstance();
        Calendar todayC = Calendar.getInstance();
        todayC.setTime(today);
        endDate.setTime(dateDebut);
        endDate.add(Calendar.DATE, duration);
        return endDate.before(todayC);
    }

    /**
     * Ajouter une nouvelle compétence au besoin de la mission + Ecriture CSV
     *
     * @param c          La nouvelle compétence à ajouter
     * @param nbEmployes Le nombre d'employés demandé pour cette compétence
     */
    public void addCompetence(Competence c, int nbEmployes) {
        MissionMgt missionMgt = new MissionMgt();
        if (!this.need.contains(c)) {
            //                missionMgt.appendCompToMission(this.id, c, nbEmployes);
            this.need.addCompetence(c, nbEmployes);
        }
    }

    /**
     * Ajouter un employé à une mission + Ecriture CSV
     *
     * @param c La compétene à laquelle sera affecté l'employé
     * @param e L'employé à ajouter
     */
    public void addEmployee(Competence c, Employee e) {
        MissionMgt missionMgt = new MissionMgt();
        if (this.need.contains(c)) {
            try {
//                missionMgt.appendEmpToMission(this.id, c, e);
                this.need.addEmployee(c, e);
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
                e1.printStackTrace();
            }
        }
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Mission) && ((Mission) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", nbEmployes=" + nbEmployes +
                ", dateDebut=" + dateDebut +
                ", duration=" + duration +
                ", status=" + status;
    }
}
