package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

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
     * Retourne l'employé correspondant à l'id passé en paramètre
     *
     * @param id L'id de l'employé
     * @return Employee
     */
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
     * <p>
     * Écriture CSV
     *
     * @param e L'employé à ajouter
     * @throws IOException IOException
     */
    public void addEmployee(Employee e) throws IOException {
        e.writeEmployeeCSV();
        this.employees.add(e);
    }

    public void deleteEmployee(Employee e) {
        if (this.employees.contains(e)) {
            this.employees.remove(e);
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
            employeeLine = line.split(separator);
            Employee newEmployee = new Employee(employeeLine[0], employeeLine[1], employeeLine[2]);

            if (!this.employees.contains(newEmployee)) {
                this.employees.add(newEmployee);
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
            missionLine = line.split(separator);
            Mission newMission = new Mission(missionLine[0], missionLine[1], missionLine[2], missionLine[3], missionLine[4], missionLine[5], this);

            if (!this.missions.contains(newMission)) {
                this.missions.add(newMission);
            }
        }
    }
}