package Model;

import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Company {

    private String name;
    ArrayList<Employee> employees = new ArrayList<>();
    ArrayList<Mission> missions = new ArrayList<>();
    // + liste_missions affectation,_employe_mission, besoin_competences


    public Company(String name) {
        this.name = name;
        try {
            importEmployeeFromCSV("liste_personnel.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /***********
     * GETTERS *
     ***********/


    /***********
     * SETTERS *
     ***********/

    public List<Employee> getEmployees() {
        return employees;
    }

    /***********
     * METHODS *
     ***********/


    /**
     * Importer tous les employés du fichier dans ArrayList<Employee> employees
     * @param fileName
     * @throws IOException
     */
    private void importEmployeeFromCSV(String fileName) throws IOException {

        String separator = ";";
        String employeeLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(fileName).getFile();


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