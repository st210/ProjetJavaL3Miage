package Model;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee extends ModifierCSV {

    private static final AtomicInteger countID = new AtomicInteger(0);
    private String id;
    private String name;
    private String firstname;
    private String entryIntoCompany;
    private ArrayList<Competence> competencesEmployee = new ArrayList<>();

    public Employee(String firstnameE, String nameE, String entry) {
        CompetenceMgt cm = new CompetenceMgt();
        this.id = String.valueOf(countID.incrementAndGet());
        this.name = nameE;
        this.firstname = firstnameE;
        this.entryIntoCompany = entry;
        try {
            competencesEmployee = cm.getCompetencesForEmp(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO: get/set liste competencesEmployee d'un employe
    // TODO: gestion de la date d'entree dans l'entp d'un employe


    /***********
     * GETTERS *
     ***********/

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getEntryIntoCompany() {
        return entryIntoCompany;
    }

    /**
     * Retourne la liste de compétences de l'employé
     *
     * @return List<Competence>
     */
    public ArrayList<Competence> getCompetencesEmployee() {
        return competencesEmployee;
    }


    /***********
     * SETTERS *
     ***********/

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEntryIntoCompany(String entryIntoCompany) {
        this.entryIntoCompany = entryIntoCompany;
    }

    public void setCompetencesEmployee(ArrayList<Competence> competencesEmployee) {
        this.competencesEmployee = competencesEmployee;
    }

    public void addCompetence(Competence c) {
        if (!this.competencesEmployee.contains(c)) {
            try {
                appendCompToEmp(this.id, c);
                this.competencesEmployee.add(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCompetence(Competence c) {
        if (this.competencesEmployee.contains(c)) {
            this.competencesEmployee.remove(c);
        }
    }

    /***********
     * METHODS *
     ***********/

    /**
     * Sauvegarder un nouvel employé dans le fichier LISTE_PERSONNEL
     *
     * @throws IOException
     */
    public void writeEmployeeCSV() throws IOException {
        String employeeLine = firstname + ";" + name + ";" + entryIntoCompany + ";" + id;
        appendNewLine(FILE_LISTE_PERSONNEL, employeeLine);
    }


    @Override
    public String toString() {
        return "[" + this.getId() + "] " + this.firstname + " " + this.name + " is employed since " + this.entryIntoCompany;
    }
}
