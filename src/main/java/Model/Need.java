package Model;

import Main.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

/**
 * Classe dévrivant les besoins d'une mission :
 * - De quelles compétences la mission à t-elle besoin ?
 * - Quel personnel répond à ce besoin ?
 */
public class Need implements IModifierCSV {

    private String idMission;


    // Décrit chaque compétence d'une mission et le nombre de personnes requises possédant cette compétence
    private HashMap<Competence, Integer> competenceInit = new HashMap<>();

    // Décrit l'état actuel de la répartion des ressources soit le nombre d'employés manquant pour une compétence
    private HashMap<Competence, ArrayList<Employee>> competenceCurrent = new HashMap<>();

    public Need(String idMission) throws Exception {
        this.idMission = idMission;

        loadCompetenceInit(idMission);
        loadCompetenceCurrent(idMission);
    }

    //***********//
    //  GETTERS  //
    //***********//

    public HashMap<Competence, Integer> getCompetenceInit() {
        return competenceInit;
    }

    public HashMap<Competence, ArrayList<Employee>> getCompetenceCurrent() {
        return competenceCurrent;
    }


    //***********//
    //  SETTERS  //
    //***********//

    public void setCompetenceInit(HashMap<Competence, Integer> competenceInit) {
        this.competenceInit = competenceInit;
    }

    public void setCompetenceCurrent(HashMap<Competence, ArrayList<Employee>> competenceCurrent) {
        this.competenceCurrent = competenceCurrent;
    }


    //***********//
    //  METHODS  //
    //***********//

    /**
     * Ajouter une nouvelle compétence aux besoins de la mission
     *
     * @param competence  La compétence à ajouter
     * @param nbEmpNeeded Le nombre d'employés nécessaires dans cette compétence
     */
    public void addCompetence(Competence competence, int nbEmpNeeded) {
        if (!this.competenceInit.containsKey(competence)) {
            this.competenceInit.put(competence, nbEmpNeeded);
            this.competenceCurrent.put(competence, new ArrayList<>());
        }
    }

    /**
     * Affecter un nouvel employé à une compétence
     *
     * @param competence La compétence concernée
     * @param employee   Le nouvel employé à affecter
     * @throws Exception Le nombre d'employés affectés dépasse le besoin
     */
    public void addEmployee(Competence competence, Employee employee) throws Exception {
        if (competenceCurrent.get(competence).size() <= competenceInit.get(competence)) {
            ArrayList<Employee> listeEmp = competenceCurrent.get(competence);
            if (listeEmp == null) {
                listeEmp = new ArrayList<>();
                listeEmp.add(employee);
                competenceCurrent.put(competence, listeEmp);
            } else {
                if (!listeEmp.contains(employee)) {
                    listeEmp.add(employee);
                    competenceCurrent.put(competence, listeEmp);
                }
            }
        } else {
            throw new Exception("Le nombre maximal de ressources pour cette compétence est atteint");
        }
    }

    /**
     * Retourne tous les employés affectés à la mission
     *
     * @return ArrayList<Employee> tous les employés de la mission
     */
    public ArrayList<Employee> getTeam() {
        ArrayList<Employee> teamList = new ArrayList<>();
        for (Map.Entry<Competence, ArrayList<Employee>> entry : competenceCurrent.entrySet()) {
            ArrayList<Employee> empList = entry.getValue();
            teamList.addAll(empList);
        }
        return teamList;
    }


    public boolean contains(Competence c) {
        return this.competenceInit.containsKey(c);
    }

    public boolean contains(Employee e) {
        return getTeam().contains(e);
    }


    /**
     * Charge à partir du fichier CSV les compétences (besoin) de la mission
     *
     * @param idMission La mission à charger
     * @throws IOException
     */
    private void loadCompetenceInit(String idMission) throws IOException {
        CompetenceMgt competenceMgt = new CompetenceMgt();

        boolean missionFound = false;
        String separatorComp = ";"; // séparateur entre les compétences
        String separatorNbPers = "~"; // séparateur entre les compétences et le nombre de personnes demandé
        String missionLine[];
        String compTab[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_COMPETENCES)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        while ((line = br.readLine()) != null && !missionFound) {
            missionLine = line.split(separatorComp);

            if (Objects.equals(missionLine[0], idMission)) {
                missionFound = true;
                for (int i = 1; i < missionLine.length; i++) {
                    compTab = missionLine[i].split(separatorNbPers);
                    addCompetence(competenceMgt.getCompetenceByIDFromCSV(compTab[0]), Integer.valueOf(compTab[1]));
                }
            }
        }

        fr.close();
        br.close();
    }


    /**
     * Charge les affectations des employés aux compétences (besoin)
     *
     * @param idMission La mission à charger
     * @throws Exception
     */
    private void loadCompetenceCurrent(String idMission) throws Exception {
        CompetenceMgt competenceMgt = new CompetenceMgt();
        Company company = Test.company;
        boolean missionFound = false;
        String separatorEmp = ";"; // separateur entre les employés
        String separatorComp = "~"; // séparateur entre l'employé et la compétence associée
        String missionLine[];
        String empTab[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_PERSONNEL)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        while ((line = br.readLine()) != null && !missionFound) {
            missionLine = line.split(separatorEmp);

            if (Objects.equals(missionLine[0], idMission)) {
                missionFound = true;

                for (int i = 1; i < missionLine.length; i++) {
                    empTab = missionLine[i].split(separatorComp);
                    addEmployee(competenceMgt.getCompetenceByIDFromCSV(empTab[0]), company.getEmployee(empTab[1]));
                }
            }
        }

        fr.close();
        br.close();
    }
}
