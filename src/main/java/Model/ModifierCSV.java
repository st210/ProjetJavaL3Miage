package Model;

import java.io.*;
import java.util.Objects;

abstract class ModifierCSV implements IModifierCSV {

    /**
     * Permet d'écrire une nouvelle ligne dans le fichier passé en paramètre
     *
     * @param file    Le nom du fichier à modifier
     * @param content La nouvelle ligne à ajouter au fichier
     * @throws IOException
     */
    void appendNewLine(String file, String content) throws IOException {
        // ATTENTION :
        // TODO l'écriture se fait dans le fichier du repertoire ./Target
        BufferedWriter bw;
        FileWriter fw;
        ClassLoader classLoader = getClass().getClassLoader();
        try {
            String path = Objects.requireNonNull(classLoader.getResource(file)).getFile();
            fw = new FileWriter(path, true);
            bw = new BufferedWriter(fw);
            bw.newLine();
            bw.write(content);
            bw.close();
            fw.close();
        } catch (IOException e) {
            System.err.println("Erreur à l'écriture du fichier - " + e.getMessage());
            throw e;
        }
    }

    /**
     * Ajoute une compétence à l'employé dans le fichier CSV
     *
     * @param idEmp L'ID de l'employé
     * @param c La compétence à ajouter à l'employé
     * @throws IOException
     */
    void appendCompToEmp(String idEmp, Competence c) throws IOException {
        boolean empFound = false;
        String separator = ";";
        String comptetencesLine[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_COMPETENCES_PERSONNEL)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while ((line = br.readLine()) != null && !empFound) {
            comptetencesLine = line.split(separator);

            if (Objects.equals(comptetencesLine[0], idEmp)) {
                empFound = true;
                bw.write(line + ";" + c.getId());
            }
        }

        if (!empFound) {
            String newLine = idEmp + ";" + c.getId();
            appendNewLine(FILE_COMPETENCES_PERSONNEL, newLine);
        }

        fw.close();
        bw.close();

        fr.close();
        br.close();
    }

    /**
     * Ajoute une compétence à la mission dans le fichier CSV
     *
     * @param idMission L'ID de la mission
     * @param c La compétence à ajouter à la mission
     * @throws IOException
     */
    void appendCompToMission(String idMission, Competence c) throws IOException {
        boolean compFound = false;
        String separator = ";";
        String competencesLine[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_COMPETENCES)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while ((line = br.readLine()) != null && !compFound) {
            competencesLine = line.split(separator);

            if (Objects.equals(competencesLine[0], idMission)) {
                compFound = true;
                bw.write(line + ";" + c.getId());
            }
        }

        if (!compFound) {
            String newLine = idMission + ";" + c.getId();
            appendNewLine(FILE_MISSION_COMPETENCES, newLine);
        }

        fw.close();
        bw.close();

        fr.close();
        br.close();
    }
}
