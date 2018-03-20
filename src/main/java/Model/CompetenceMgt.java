package Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;


public class CompetenceMgt implements IModifierCSV {

    /**
     * Retourne les compétence d'un employé dont l'ID est passé en paramètre
     *
     * @param idEmp l'id de l'employé
     * @return ArrayList<Competence> Les compétences de l'employé
     * @throws IOException
     */
    public ArrayList<Competence> getCompetencesForEmp(String idEmp) throws IOException {
        ArrayList<Competence> competencesEmp = new ArrayList<>();
        ArrayList<Competence> allCompetences = new ArrayList<>();

        allCompetences = importCompetencesFromCSV(FILE_LISTE_COMPETENCES);
        String separator = ";";
        String comptetencesLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_COMPETENCES_PERSONNEL)).getFile();

        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            comptetencesLine = line.split(separator);

            // Si la ligne concerne le bon employé
            if (Objects.equals(comptetencesLine[0], idEmp)) {
                // Pour toutes les compétences de cette ligne (sous forme d'ID)
                for (int i = 1; i < comptetencesLine.length; i++) {
                    // On trouve la compétence correspondante
                    for (Competence c : allCompetences) {
                        // On ajoute la compétence correspondante à la liste de compétences de l'employé
                        if (Objects.equals(c.getId(), comptetencesLine[i]) && !competencesEmp.contains(c)) {
                            competencesEmp.add(c);
                        }
                    }
                }
            }
        }

        return competencesEmp;
    }

    /**
     * Retourne toutes les compétences contenues dans le fichier passé en paramètre
     *
     * @param fileName le nom du fichier contenant les compétences
     * @return List<Competence>
     * @throws IOException
     */
    public ArrayList<Competence> importCompetencesFromCSV(String fileName) throws IOException {

        ArrayList<Competence> competences = new ArrayList<>();

        String separator = ";";
        String comptetencesLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(fileName)).getFile();

        FileReader csvFile = new FileReader(path);
        BufferedReader br = new BufferedReader(csvFile);

        while ((line = br.readLine()) != null) {
            comptetencesLine = line.split(separator);
            Competence newCompetence = new Competence(comptetencesLine[0], comptetencesLine[1], comptetencesLine[2]);

            if (!competences.contains(newCompetence)) {
                competences.add(newCompetence);
            }
        }
        return competences;
    }

    /**
     * Retourne la compétence correspondante à l'ID passé en paramètre
     *
     * @param id Id de la compétence
     * @return Competence
     */
    public Competence getCompetenceByID(String id) {
        ArrayList<Competence> allComp = new ArrayList<>();
        try {
            allComp = importCompetencesFromCSV(FILE_LISTE_COMPETENCES);
            for (Competence c : allComp) {
                if (Objects.equals(c.getId(), id)) {
                    return c;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.err.println("La compétence n'existe pas"); // TODO Throw exception
        return null;
    }
}
