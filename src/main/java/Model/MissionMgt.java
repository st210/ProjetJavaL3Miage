package Model;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MissionMgt extends ModifierCSV {
    /**
     * Ajoute une compétence à la mission dans le fichier CSV
     *
     * @param idMission L'ID de la mission
     * @param c         La compétence à ajouter à la mission
     * @param nbPers    Le nombre de personnes demandé pour cette compétence
     * @throws IOException
     */
    void appendCompToMission(String idMission, Competence c, int nbPers) throws IOException {
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

                bw.write(line + separatorComp + c.getId() + separatorNbPers + nbPers);
            }
        }

        if (!missionFound) {
            String newLine = idMission + separatorComp + c.getId() + separatorNbPers + nbPers;
            appendNewLine(FILE_MISSION_COMPETENCES, newLine);
        }

        fw.close();
        bw.close();

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
    void appendEmpToMission(String idMission, Competence comp, Employee emp) throws IOException {
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

                bw.write(line + separatorEmp + comp.getId() + separatorComp + emp.getId());
            }
        }

        if (!missionFound) {
            String newLine = idMission + separatorEmp + comp.getId() + separatorComp + emp.getId();
            appendNewLine(FILE_MISSION_PERSONNEL, newLine);
        }

        fw.close();
        bw.close();

        fr.close();
        br.close();
    }


}
