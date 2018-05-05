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


    public Company(String name) {
        this.name = name;
        try {
            importEmployeeFromCSV(FILE_LISTE_PERSONNEL);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***********
     * GETTERS *
     ***********/

    /**
     * Retourne tous les employés de l'entreprise
     *
     * @return ArrayList<Employee> La liste de tous les employés
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * Retourne l'employé correspondant à l'id passé en paramètre
     *
     * @param id L'id de l'employé
     * @return
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

    /***********
     * SETTERS *
     ***********/


    /***********
     * METHODS *
     ***********/

    /**
     * Ajoute un nouvel employé à la liste d'employés de l'entreprise + écriture sur le fichier
     *
     * @param e L'employé à ajouter
     * @throws IOException
     */
    public void addEmployee(Employee e) throws IOException {
        e.writeEmployeeCSV();
        this.employees.add(e);
    }

    /**
     * Importer tous les employés du fichier dans ArrayList<Employee> employees
     *
     * @param fileName le nom du fichier
     * @throws IOException
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

}