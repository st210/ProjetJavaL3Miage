package Model;

import Main.Test;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

public class Employee extends ModifierCSV {

    private static final AtomicInteger nbEmployees = new AtomicInteger(0);
    private String id;
    private String name;
    private String firstname;
    private String entryIntoCompany;
    private ArrayList<Competence> competencesEmployee = new ArrayList<>();
    private boolean taken = false;

    public Employee(String firstnameE, String nameE, String entry) {
        CompetenceMgt cm = new CompetenceMgt();
        this.id = String.valueOf(nbEmployees.incrementAndGet());
        this.name = nameE;
        this.firstname = firstnameE;
        this.entryIntoCompany = entry;
        try {
            competencesEmployee = cm.getCompetencesForEmpFromCSV(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Employee(String id, String name, String firstname, String entryIntoCompany) {
        CompetenceMgt cm = new CompetenceMgt();
        this.id = id;
        this.name = name;
        this.firstname = firstname;
        this.entryIntoCompany = entryIntoCompany;
        try {
            competencesEmployee = cm.getCompetencesForEmpFromCSV(id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //***********//
    //  GETTERS  //
    //***********//

    public static AtomicInteger getNbEmployees() {
        return nbEmployees;
    }

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

    public Date getDate() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return formatter.parse(this.entryIntoCompany);
    }

    public boolean isTaken() {
        return taken;
    }


    /**
     * Retourne la liste de compétences de l'employé
     *
     * @return List<Competence>
     */
    public ArrayList<Competence> getCompetencesEmployee() {
        return competencesEmployee;
    }


    //***********//
    //  SETTERS  //
    //***********//

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

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    /**
     * Ajouter une nouvelle compétence à l'employé
     * Écriture CSV
     *
     * @param c La compétence à ajouter
     */
    public void addCompetence(Competence c) {
        if (!this.competencesEmployee.contains(c)) {
            this.competencesEmployee.add(c);
        }
    }

    /**
     * Enlever une compétence de l'empoyé
     *
     * @param c La compétence à enlever à l'employé
     */
    public void removeCompetence(Competence c) {
        if (this.competencesEmployee.contains(c)) {
            this.competencesEmployee.remove(c);
        }
    }

    //***********//
    //  METHODS  //
    //***********//

    /**
     * Retourne vrai si l'employé travaille sur la mission passsée en paramètre
     *
     * @param m La mission dans laquelle on cherche l'employé
     * @return Boolean
     */
    public boolean workingForMission(Mission m) {
        return m.getTeam().contains(this);
    }

    @Override
    public String toString() {
        return "[" + this.getId() + "] " + this.firstname + " " + this.name + " is employed since " + this.entryIntoCompany;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Employee) && ((Employee) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
