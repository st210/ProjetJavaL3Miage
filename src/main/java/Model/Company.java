package Model;

import Main.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Company implements IModifierCSV {

    private String name;
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Mission> missions = new ArrayList<>();
    // + liste_missions affectation,_employe_mission, besoin_competences


    public Company() {
        try {
            importEmployeeFromCSV(FILE_LISTE_PERSONNEL);
            importMissionsFromCSV(FILE_LISTE_MISSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Company(String name) {
        this.name = name;
        try {
            importEmployeeFromCSV(FILE_LISTE_PERSONNEL);
            importMissionsFromCSV(FILE_LISTE_MISSION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //***********//
    //  GETTERS  //
    //***********//

    /**
     * Retourne tous les employés de l'entreprise
     *
     * @return ArrayList<Employee> La liste de tous les employés
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    /**
     * Retourne les missions non terminées
     *
     * @return ArrayList<Mission> Liste des missions non terminnées
     */
    public ArrayList<Mission> getMissionNotCompleted() {
        ArrayList<Mission> missions = new ArrayList<>();
        for (Mission m : this.missions) {
            if (m.getStatus() != MissionStatus.COMPLETED) {
                missions.add(m);
            }
        }
        return missions;
    }

    /**
     * Retourne les missions terminées
     *
     * @return ArrayList<Mission> Liste des missions terminnées
     */
    public ArrayList<Mission> getMissionCompleted() {
        ArrayList<Mission> missions = new ArrayList<>();
        for (Mission m : this.missions) {
            if (m.getStatus() == MissionStatus.COMPLETED) {
                missions.add(m);
            }
        }
        return missions;
    }

    /**
     * Retourne les missions en cours
     *
     * @return ArrayList<Mission> Liste des missions non terminnées
     */
    public ArrayList<Mission> getMissionInProgress() {
        ArrayList<Mission> missions = new ArrayList<>();
        for (Mission m : this.missions) {
            if (m.getStatus() == MissionStatus.PROGRESS) {
                missions.add(m);
            }
        }
        return missions;
    }

    /**
     * Retourne les missions en préparation
     *
     * @return ArrayList<Mission> Liste des missions non terminnées
     */
    public ArrayList<Mission> getMissionPreparation() {
        ArrayList<Mission> missions = new ArrayList<>();
        for (Mission m : this.missions) {
            if (m.getStatus() == MissionStatus.PREPARATION) {
                missions.add(m);
            }
        }

        return missions;
    }

    /**
     * Retourne les missions programmées
     *
     * @return ArrayList<Mission> Liste des missions non terminnées
     */
    public ArrayList<Mission> getMissionScheduled() {
        ArrayList<Mission> missions = new ArrayList<>();
        for (Mission m : this.missions) {
            if (m.getStatus() == MissionStatus.SCHEDULED) {
                missions.add(m);
            }
        }

        return missions;
    }

    /**
     * Retourne la date de la prochaine mission à se terminer
     *
     * @return Date
     */
    public Date getDateNextFinMiss() {
        Date dateCurrent, dateMin = null;
        Calendar c = Calendar.getInstance();
        int cpt = 0;
        for (Mission m : this.missions) {
            c.setTime(m.getDateDebut());
            c.add(Calendar.DATE, m.getDuration());
            dateCurrent = c.getTime();
            if (cpt == 0) {
                dateMin = dateCurrent;
                cpt++;
            }
            if (dateCurrent.before(dateMin)) {
                dateMin = dateCurrent;
                cpt++;
            }
        }
        return dateMin;
    }

    /**
     * Retourne la date de la dernière mision lancée
     *
     * @return Date Dernère mission lancée
     */
    public Date getDateLastLaunch() {
        AtomicReference<Date> lastDate = new AtomicReference<>(new Date());
        AtomicInteger cpt = new AtomicInteger();
        lastDate.set(missions.get(0).getDateDebut());
        this.missions.forEach(mission -> {
            if (cpt.get() <= 0) {
                lastDate.set(mission.getDateDebut());
                cpt.getAndIncrement();
            }
            if (mission.getStatus() == MissionStatus.PROGRESS && mission.getDateDebut().after(lastDate.get())) {
                lastDate.set(mission.getDateDebut());
            }
        });
        return lastDate.get();
    }

    public Employee getEmployee(String id) {
        for (Employee e : this.employees) {
            if (e.getId().equals(id)) {
                return e;
            }
        }
        System.err.println("Employee not found");
        return null;
    }


    //***********//
    //  SETTERS  //
    //***********//


    //***********//
    //  METHODS  //
    //***********//

    /**
     * Ajoute un nouvel employé à la liste d'employés de l'entreprise
     *
     * @param e L'employé à ajouter
     */
    public void addEmployee(Employee e) {
        if (!this.employees.contains(e)) {
            this.employees.add(e);
        }
    }

    /**
     * Supprime un employé de l'entreprise
     *
     * @param e l'employé à supprimer
     */
    public void removeEmployee(Employee e) {
        if (this.employees.contains(e)) {
            this.employees.remove(e);
        }
    }

    /**
     * Ajouter une mission à l'entreprise
     *
     * @param m La mission à ajouter
     */
    public void addMission(Mission m) {
        this.missions.add(m);
    }

    /**
     * Supprimer une mission de l'entreprise
     *
     * @param m La mission a supprimer
     */
    public void removeMission(Mission m) {
        if (m != null && this.missions.contains(m)) {
            this.missions.remove(m);
        }
    }

    /**
     * Retoune tous les employés non occupés
     *
     * @return ArrayList<Employee> contenant tous les employés non occupés
     */
    public ArrayList<Employee> freeEmployee() {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee e : employees) {
            if (!e.isTaken()) {
                list.add(e);
            }
        }
        return list;
    }

    public ArrayList<Employee> occupEmployee() {
        ArrayList<Employee> list = new ArrayList<>();
        for (Employee e : employees) {
            if (e.isTaken()) {
                list.add(e);
            }
        }
        return list;
    }

    public void setAllOccupiedEmp() {
        for (Mission m : missions) {
            for (Employee e : m.getTeam()) {
                modifyEmpTaken(e.getId());
            }
        }
    }

    private void modifyEmpTaken(String id) {
        int cpt = 0;
        for (Employee e : this.employees) {
            if (e.getId().equals(id)) {
                Test.company.employees.get(cpt).setTaken(true);
            }
            cpt++;
        }
    }

    /**
     * Importer tous les employés du fichier dans ArrayList<Employee> employees
     *
     * @param fileName le nom du fichier
     * @throws IOException IOException
     */
    private void importEmployeeFromCSV(String fileName) throws IOException {

        String separator = ";";
        String employeeLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getFile();


        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            if (!line.equals("")) {
                employeeLine = line.split(separator);
                Employee newEmployee = new Employee(employeeLine[0], employeeLine[1], employeeLine[2]);

                if (!this.employees.contains(newEmployee)) {
                    this.employees.add(newEmployee);
                }
            }
        }
    }


    /**
     * Importer toutes les mission des fichiers CSV
     *
     * @param fileName le nom du fichier
     * @throws IOException IOException
     */
    private void importMissionsFromCSV(String fileName) throws IOException {

        String separator = ";";
        String missionLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getFile();


        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            if (!line.equals("")) {
                missionLine = line.split(separator);
                Mission newMission = new Mission(missionLine[0], missionLine[1], missionLine[2], missionLine[3], missionLine[4], missionLine[5], this);

                if (!this.missions.contains(newMission)) {
                    this.missions.add(newMission);
                }
            }
        }
    }
}