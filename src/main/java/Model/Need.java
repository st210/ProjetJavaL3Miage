package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Need {

    // Décrit chaque compétence d'une mission et le nombre de personnes requises possédant cette compétence
    private HashMap<Competence, Integer> competenceInit = new HashMap<>();

    // Décrit l'état actuel de la répartion des ressources soit le nombre d'employés manquant pour une compétence
    private HashMap<Competence, ArrayList<Employee>> competenceCurrent = new HashMap<>();


    /**
     * Ajouter une nouvelle compétence au besoin de la mission
     *
     * @param competence La compétence à ajouter
     * @param nbEmpNeeded Le nombre d'employés nécessaires dans cette compétence
     */
    public void addCompetence(Competence competence, int nbEmpNeeded) {
        if (!this.competenceInit.containsKey(competence)) {
            this.competenceInit.put(competence, nbEmpNeeded);
            this.competenceCurrent.put(competence, new ArrayList<Employee>());
        }
    }

    /**
     * Affecter un nouvel employé à une compétence
     *
     * @param competence La compétence concernée
     * @param employee Le nouvel employé à affecter
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
}
