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

    /**
     * Ajouter une nouvelle compétence à l'employé
     * Écriture CSV
     *
     * @param c La compétence à ajouter
     */
    public void addCompetence(Competence c) {
        EmployeeMgt employeeMgt = new EmployeeMgt();
        if (!this.competencesEmployee.contains(c)) {
            try {
                employeeMgt.appendCompToEmp(this.id, c);
                this.competencesEmployee.add(c);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void removeCompetence(Competence c) {
        if (this.competencesEmployee.contains(c)) {
            // TODO Remove from file
            this.competencesEmployee.remove(c);
        }
    }

    //***********//
    //  METHODS  //
    //***********//

    /**
     * Sauvegarder un nouvel employé dans le fichier LISTE_PERSONNEL
     *
     * @throws IOException
     */
    public void writeEmployeeCSV() throws IOException {
        String employeeLine = firstname + ";" + name + ";" + entryIntoCompany + ";" + id;
        appendNewLine(FILE_LISTE_PERSONNEL, employeeLine, false);
    }

    @Override
    public String toString() {
        return "[" + this.getId() + "] " + this.firstname + " " + this.name + " is employed since " + this.entryIntoCompany;
    }
}
