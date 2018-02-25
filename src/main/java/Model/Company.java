package Model;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Company implements CompanyDAO{
    private int id;
    private String nom;
    List<Employee> employees;
    List<Competence> competences;
    List<Mission> missions;
    // + liste_missions affectation,_employe_mission, besoin_competences


    public Company(String nom) {
        this.id = 0;
        this.nom = nom;
        employees = new ArrayList<Employee>();
        try {
            FileReader csvFile = new FileReader("resources/liste_personnel.csv");
            CSVReader reader = new CSVReader(csvFile, ';');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                Employee e = new Employee(nextLine[0], nextLine[1], nextLine[2]);
                employees.add(e);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Employee> showAllEmployees() {
        List<Employee> list = null;

        try {
            FileReader csvFile = new FileReader("resources/liste_personnel.csv");
            CSVReader reader = new CSVReader(csvFile, ';');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("[" + nextLine[3] + "] " + nextLine[0] + " " + nextLine[1] + ", employé depuis le " + nextLine[2]);
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
    public Employee findEmployeeById(int id){
        return employees.get(id);
    }

    @Override
    public void insertEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Employee n°" + employee.getId() + " successfully added from database.");
    }
    
    @Override
    public void deleteEmployee(Employee employee){
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
