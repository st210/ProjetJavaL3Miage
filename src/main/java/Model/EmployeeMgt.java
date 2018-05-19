package Model;

import Main.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class EmployeeMgt extends ModifierCSV {

//    /**
//     * Ajoute une compétence à l'employé dans le fichier CSV
//     *
//     * @param idEmp L'ID de l'employé
//     * @param c     La compétence à ajouter à l'employé
//     * @throws IOException
//     */
//    void appendCompToEmp(String idEmp, Competence c) throws IOException {
//        boolean empFound = false;
//        String separator = ";";
//        String comptetencesLine[];
//        String line;
//        ClassLoader classLoader = getClass().getClassLoader();
//        String path = Objects.requireNonNull(classLoader.getResource(FILE_COMPETENCES_PERSONNEL)).getFile();
//        FileReader fr = new FileReader(path);
//        BufferedReader br = new BufferedReader(fr);
//
//        FileWriter fw = new FileWriter(path, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        while ((line = br.readLine()) != null && !empFound) {
//            comptetencesLine = line.split(separator);
//
//            if (Objects.equals(comptetencesLine[0], idEmp)) {
//                empFound = true;
//                bw.write(line + ";" + c.getId());
//            }
//        }
//
//        if (!empFound) {
//            String newLine = idEmp + ";" + c.getId();
//            appendNewLine(FILE_COMPETENCES_PERSONNEL, newLine, false);
//        }
//
//        fw.close();
//        bw.close();
//
//        fr.close();
//        br.close();
//    }

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
