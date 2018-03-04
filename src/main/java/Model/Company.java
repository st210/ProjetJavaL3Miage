package Model;

import com.opencsv.CSVReader;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Company implements CompanyDAO {
    List<Employee> employees;
    List<Competence> competences;
    List<Mission> missions;
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;
    private String nom;
    // + liste_missions affectation,_employe_mission, besoin_competences


    public Company(String nom, String path) {
        this.nom = nom;
        employees = new ArrayList<Employee>();
        try {
            FileReader csvFile = new FileReader(path);
            CSVReader reader = new CSVReader(csvFile, ';');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                if (nextLine != null) {
                    Employee e = new Employee(nextLine[0], nextLine[1], nextLine[2]);
                    employees.add(e);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.id = countID.incrementAndGet();
    }

    public void showAllEmployees(String path) {
        try {
            FileReader csvFile = new FileReader(path);
            CSVReader reader = new CSVReader(csvFile, ';');

            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                System.out.println("[" + nextLine[3] + "] " + nextLine[0] + " " + nextLine[1] + " is employed since " + nextLine[2]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Employee> findAll() {
        return employees;
    }

    @Override
    public Employee findEmployeeById(int id) {
        return employees.get(id-1);
    }

    @Override
    public void insertEmployee(Employee employee) {
        employees.add(employee);
        System.out.println("Employee n°" + employee.getId() + " successfully added from database.");
    }

    @Override
    public void deleteEmployee(Employee employee) {
        employees.remove(employee);
        System.out.println("Employee n°" + employee.getId() + " successfully removed from database.");
    }

    @Override
    public void updateEmployeeName(Employee employee, String name) {
        employees.get(employee.getId()).setName(name);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

    @Override
    public void updateEmployeeFirstName(Employee employee, String firstname) {
        employee.setFirstName(firstname);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

    @Override
    public void updateEmployeeEntry(Employee employee, String entry) {
        employees.get(employee.getId()).setEntryIntoCompany(entry);
        System.out.println("Employee n°" + employee.getId() + " successfully updated in the database.");
    }

}
