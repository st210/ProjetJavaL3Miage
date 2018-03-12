package Model;

import com.opencsv.bean.CsvBindByName;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee {

    private static final AtomicInteger countID = new AtomicInteger(0);
    @CsvBindByName(column = "Identifiant", required = true)
    private int id;
    @CsvBindByName(column = "Nom", required = true)
    private String name;
    @CsvBindByName(column = "Prenom", required = true)
    private String firstname;
    @CsvBindByName(column = "Date entrée entreprise", required = true)
    private String entryIntoCompany;
    private List<Competence> competencesEmployee;

    public Employee(String firstnameE, String nameE, String entry) {
        this.name = nameE;
        this.firstname = firstnameE;
        this.entryIntoCompany = entry;
        this.competencesEmployee = null;
        this.id = countID.incrementAndGet();
    }

    // TODO: get/set liste competencesEmployee d'un employe
    // TODO: gestion de la date d'entree dans l'entp d'un employe


    /***********
     * GETTERS *
     ***********/

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstname;
    }

    public List<Competence> getCompetencesEmployee() {
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

    public void setCompetencesEmployee(List<Competence> competencesEmployee) {
        this.competencesEmployee = competencesEmployee;
    }

    public void addCompetence(Competence c) {
        if (!this.competencesEmployee.contains(c)) {
            this.competencesEmployee.add(c);
        }
    }

    public void removeCompetence(Competence c) {
        if (this.competencesEmployee.contains(c)) {
            this.competencesEmployee.remove(c);
        }
    }

    @Override
    public String toString() {
        return "[" + this.getId() + "] " + this.firstname + " " + this.name + " is employed since " + this.entryIntoCompany;
    }
}
