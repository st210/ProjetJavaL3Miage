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
    public void appendNewLine(String file, String content) throws IOException {
        // ATTENTION : l'écriture se fait dans le fichier du repertoire ./Target

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
     * Ajoute une compétence à la mission dans le fichier CSV
     *
     * @param idMission L'ID de la mission
     * @param c         La compétence à ajouter à la mission
     * @param nbPers    Le nombre de personnes demandé pour cette compétence
     * @throws IOException
     */
    public void appendCompToMission(String idMission, Competence c, int nbPers) throws IOException {
        boolean missionFound = false;
        String separatorComp = ";"; // séparateur entre les compétences
        String separatorNbPers = "~"; // séparateur entre les compétences et le nombre de personnes demandé
        String missionLine[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_COMPETENCES)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while ((line = br.readLine()) != null && !missionFound) {
            missionLine = line.split(separatorComp);

            if (Objects.equals(missionLine[0], idMission)) {
                missionFound = true;

                bw.write(separatorComp + c.getId() + separatorNbPers + nbPers);
            }
        }

        if (!missionFound) {
            String newLine = idMission + separatorComp + c.getId() + separatorNbPers + nbPers;
            appendNewLine(FILE_MISSION_COMPETENCES, newLine);
        }

        fw.flush();
        bw.flush();

        fr.close();
        br.close();
    }

    /**
     * Ajoute un employé à la mission dans le fichier CSV
     *
     * @param idMission L'id de la mission
     * @param comp      La compétence pour laquelle on ajoute l'employé
     * @param emp       L'employé à ajouter
     * @throws IOException
     */
    public void appendEmpToMission(String idMission, Competence comp, Employee emp) throws IOException {
        boolean missionFound = false;
        String separatorEmp = ";"; // separateur entre les employés
        String separatorComp = "~"; // séparateur entre l'employé et la compétence associée
        String missionLine[];
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_PERSONNEL)).getFile();
        FileReader fr = new FileReader(path);
        BufferedReader br = new BufferedReader(fr);

        FileWriter fw = new FileWriter(path, true);
        BufferedWriter bw = new BufferedWriter(fw);

        while ((line = br.readLine()) != null && !missionFound) {
            missionLine = line.split(separatorEmp);

            if (Objects.equals(missionLine[0], idMission)) {
                missionFound = true;

                bw.write(separatorEmp + comp.getId() + separatorComp + emp.getId());
            }
        }

        if (!missionFound) {
            String newLine = idMission + separatorEmp + comp.getId() + separatorComp + emp.getId();
            appendNewLine(FILE_MISSION_PERSONNEL, newLine);
        }

        fw.flush();
        bw.flush();

        fr.close();
        br.close();
    }
}
