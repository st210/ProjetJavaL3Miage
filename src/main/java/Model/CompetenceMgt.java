package Model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class CompetenceMgt {

    private static final String FILE_LISTE_COMPETENCES = "liste_competences.csv";
    private static final String FILE_COMPETENCES_PERSONNEL = "competences_personnel.csv";

    public ArrayList<Competence> getCompetencesForEmp(String idEmp) throws IOException {
        ArrayList<Competence> competencesEmp = new ArrayList<>();
        ArrayList<Competence> allCompetences = new ArrayList<>();

        allCompetences = importCompetencesFromCSV(FILE_LISTE_COMPETENCES);
        String separator = ";";
        String comptetencesLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(FILE_COMPETENCES_PERSONNEL).getFile();

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
     * @param fileName the name of the file containing all the skills
     * @return List<Competence>
     * @throws IOException
     */
    public ArrayList<Competence> importCompetencesFromCSV(String fileName) throws IOException {

        ArrayList<Competence> competences = new ArrayList<>();

        String separator = ";";
        String comptetencesLine[];
        String line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = classLoader.getResource(fileName).getFile();

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
//    public Competence getCompetenceByID(String id) {
//        ArrayList<Competence> allComp = new ArrayList<>();
//        try {
//            allComp = importCompetencesFromCSV(FILE_LISTE_COMPETENCES);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        for (Competence c : allComp) {
//            if (Objects.equals(c.getId(), id)) {
//                return c;
//            }
//        }
//        return null;
//    }
}
