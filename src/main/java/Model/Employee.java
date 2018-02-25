package Model;

import com.opencsv.bean.CsvBindByName;

import java.util.Date;
import java.util.List;

public class Employee {
    @CsvBindByName(column = "Identifiant", required = true)
    private int id;
    @CsvBindByName(column = "Nom", required = true)
    private String name;
    @CsvBindByName(column = "Prenom", required = true)
    private String firstname;
    @CsvBindByName(column = "Date entrée entreprise", required = true)
    private String entryIntoCompany;
    private List<Competence> competences;

    public Employee(String nameE, String firstnameE, String entry) {
        this.id = 0;
        this.name = nameE;
        this.firstname = firstnameE;
        this.entryIntoCompany = entry;
        this.competences = null;
    }

    // TODO: get/set liste competences d'un employe
    // TODO: gestion de la date d'entree dans l'entp d'un employe


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEntryIntoCompany(String entryIntoCompany) {
        this.entryIntoCompany = entryIntoCompany;
    }

    public void setCompetences(List<Competence> competences) {
        this.competences = competences;
    }

    @Override
    public String toString() {
        return this.firstname + " " + this.name + " est employé depuis le " + this.entryIntoCompany;
    }
}
