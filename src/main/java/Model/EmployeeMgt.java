package Model;

import Main.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class EmployeeMgt extends ModifierCSV {

    /**
     * Retourne la liste de tous les employés contenus dans le fichier CSV
     *
     * @return ArrayList<Employee> La liste des employés
     * @throws IOException Erreur lecture/ecriture du fichier CSV
     */
    public ArrayList<Employee> getAllEmployeesCSV() throws IOException {
        ArrayList<Employee> employees = new ArrayList<>();
        String separator = ";";
        String employeeLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_LISTE_PERSONNEL)).getFile();


        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            if (!line.equals("")) {
                employeeLine = line.split(separator);
                Employee newEmployee = new Employee(employeeLine[3], employeeLine[0], employeeLine[1], employeeLine[2]);

                if (!employees.contains(newEmployee)) {
                    employees.add(newEmployee);
                }
            }
        }
        return employees;
    }

    /**
     * Sauvegarder dans le fichier CSV les compétences associées à chaque employé
     *
     * @throws IOException Erreur de lecture/ecriture du fichier CSV
     */
    public void saveAllComp() throws IOException {
        StringBuilder line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_COMPETENCES_PERSONNEL)).getFile();
        File fold = new File(path);
        fold.delete();
        for (Employee e : Test.company.getEmployees()) {
            line = new StringBuilder(e.getId());
            for (Competence c : e.getCompetencesEmployee()) {
                line.append(";").append(c.getId());
            }
            appendNewLine(path, line.toString(), true);
        }
    }

    /**
     * Sauvegarde la liste des employés dans le fichier CSV
     *
     * @throws IOException Erreur lecture/ecriture du fichier CSV
     */
    public void saveAllEmployee() throws IOException {
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_LISTE_PERSONNEL)).getFile();
        File fold = new File(path);
        fold.delete();
        for (Employee e : Test.company.getEmployees()) {
            line = e.getFirstname() + ";" + e.getName() + ";" + e.getEntryIntoCompany() + ";" + e.getId();
            appendNewLine(path, line, true);
        }
    }

    /**
     * Retourne la liste des employés possédant la compétence passée en paramètre
     *
     * @param competence La compétence que l'on souhaite chez l'employé
     * @return ArrayList<Employee> Liste d'employés
     */
    public ArrayList<Employee> findEmpForComp(Competence competence) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Employee e : Test.company.getEmployees()) {
            if (e.getCompetencesEmployee().contains(competence)) {
                employees.add(e);
            }
        }
        return employees;
    }
}
