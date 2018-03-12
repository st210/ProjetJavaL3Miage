package Model;

import com.opencsv.CSVReader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Company implements CompanyDAO {

    private String name;
    List<Employee> employees = new ArrayList<>();
    List<Competence> competences = new ArrayList<>();
    List<Mission> missions = new ArrayList<>();
//    private static final AtomicInteger countID = new AtomicInteger(0);
//    private int id;
    // + liste_missions affectation,_employe_mission, besoin_competences


    public Company(String name) {
        this.name = name;
        try {
            importEmployeeFromCSV("liste_personnel.csv");
            importCompetencesFromCSV("liste_competences.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    private void importCompetencesFromCSV(String fileName) throws IOException {

        String separator = ";";
        String comptetencesLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(fileName).getFile();


        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            comptetencesLine = line.split(separator);
            Competence newCompetence = new Competence(comptetencesLine[0], comptetencesLine[1]);

            if (!this.competences.contains(newCompetence)) {
                this.competences.add(newCompetence);
            }
        }
    }

    public List<Employee> showAllEmployees() {
        List<Employee> list = null;

        try {
            FileReader csvFile = new FileReader("../resources/liste_personnel.csv");
            CSVReader reader = new CSVReader(csvFile, ';');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("[" + nextLine[3] + "] " + nextLine[0] + " " + nextLine[1] + " is employed since " + nextLine[2]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee findEmployeeById(int id) {
        return employees.get(id);
    }

    @Override
    public void insertEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Employee n°" + employee.getId() + " successfully added from database.");
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employees.remove(employee.getId());
        System.out.println("Employee n°" + employee.getId() + " successfully removed from database.");
    }

    @Override
    public void updateEmployeeName(Employee employee, String name) {
        employees.get(employee.getId()).setName(name);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

    @Override
    public void updateEmployeeFirstName(Employee employee, String firstname) {
        employees.get(employee.getId()).setFirstName(firstname);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

    @Override
    public void updateEmployeeEntry(Employee employee, String entry) {
        employees.get(employee.getId()).setEntryIntoCompany(entry);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

}
