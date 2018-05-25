package Model;

import Main.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class MissionMgt extends ModifierCSV {
//    /**
//     * Ajoute une compétence à la mission dans le fichier CSV
//     *
//     * @param idMission L'ID de la mission
//     * @param c         La compétence à ajouter à la mission
//     * @param nbPers    Le nombre de personnes demandé pour cette compétence
//     * @throws IOException
//     */
//    void appendCompToMission(String idMission, Competence c, int nbPers) throws IOException {
//        boolean missionFound = false;
//        String separatorComp = ";"; // séparateur entre les compétences
//        String separatorNbPers = "~"; // séparateur entre les compétences et le nombre de personnes demandé
//        String missionLine[];
//        String line;
//        ClassLoader classLoader = getClass().getClassLoader();
//        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_COMPETENCES)).getFile();
//        FileReader fr = new FileReader(path);
//        BufferedReader br = new BufferedReader(fr);
//
//        FileWriter fw = new FileWriter(path, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        while ((line = br.readLine()) != null && !missionFound) {
//            missionLine = line.split(separatorComp);
//
//            if (Objects.equals(missionLine[0], idMission)) {
//                missionFound = true;
//
//                bw.write(separatorComp + c.getId() + separatorNbPers + nbPers);
//            }
//        }
//
//        if (!missionFound) {
//            String newLine = idMission + separatorComp + c.getId() + separatorNbPers + nbPers;
//            appendNewLine(FILE_MISSION_COMPETENCES, newLine, false);
//        }
//
//        fw.flush();
//        bw.flush();
//
//        fr.close();
//        br.close();
//    }

//    /**
//     * Ajoute un employé à la mission dans le fichier CSV
//     *
//     * @param idMission L'id de la mission
//     * @param comp      La compétence pour laquelle on ajoute l'employé
//     * @param emp       L'employé à ajouter
//     * @throws IOException
//     */
//    void appendEmpToMission(String idMission, Competence comp, Employee emp) throws IOException {
//        boolean missionFound = false;
//        String separatorEmp = ";"; // separateur entre les employés
//        String separatorComp = "~"; // séparateur entre l'employé et la compétence associée
//        String missionLine[];
//        String line;
//        ClassLoader classLoader = getClass().getClassLoader();
//        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_PERSONNEL)).getFile();
//        FileReader fr = new FileReader(path);
//        BufferedReader br = new BufferedReader(fr);
//
//        FileWriter fw = new FileWriter(path, true);
//        BufferedWriter bw = new BufferedWriter(fw);
//
//        while ((line = br.readLine()) != null && !missionFound) {
//            missionLine = line.split(separatorEmp);
//
//            if (Objects.equals(missionLine[0], idMission)) {
//                missionFound = true;
//
//                bw.write(separatorEmp + comp.getId() + separatorComp + emp.getId());
//            }
//        }
//
//        if (!missionFound) {
//            String newLine = idMission + separatorEmp + comp.getId() + separatorComp + emp.getId();
//            appendNewLine(FILE_MISSION_PERSONNEL, newLine, false);
//        }
//
//        fw.flush();
//        bw.flush();
//
//        fr.close();
//        br.close();
//    }

    /**
     * Sauvegarder toutes les missions dans le fichier CSV
     *
     * @throws IOException Erreur de lecture/ecriture dans le fichier CSV
     */
    public void saveAllMissions() throws IOException {
        String line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_LISTE_MISSION)).getFile();
        File fold = new File(path);
        fold.delete();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

        for (Mission m : Test.company.getMissions()) {
            line = m.getId() + ";" + m.getName() + ";" + m.getNbEmployes() + ";" + formatter.format(m.getDateDebut()) + ";" + m.getDuration() + ";" + m.getStatus().name();
            appendNewLine(path, line, true);
        }
    }

    /**
     * Sauvegarder les besoins en compétences des missions
     *
     * @throws IOException Erreur de lecture/ecriture du fichier CSV
     */
    public void saveMissionsComp() throws IOException {
        StringBuilder line;

        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_COMPETENCES)).getFile();
        File fold = new File(path);
        fold.delete();

        for (Mission m : Test.company.getMissions()) {
            line = new StringBuilder(m.getId());
            StringBuilder finalLine = new StringBuilder();
            m.getNeed().getCompetenceInit().forEach((competence, integer) -> {
                finalLine.append(";").append(competence.getId()).append("~").append(integer);
            });
            line.append(finalLine);
            appendNewLine(path, line.toString(), true);
        }
    }

    /**
     * Sauvegarder les équipes mission
     *
     * @throws IOException Erreur de lecture/ecriture du fichier CSV
     */
    public void saveMissionsEmp() throws IOException {
        StringBuilder line;
        ClassLoader classLoader = getClass().getClassLoader();
        String path = Objects.requireNonNull(classLoader.getResource(FILE_MISSION_PERSONNEL)).getFile();
        File fold = new File(path);
        fold.delete();

        for (Mission m : Test.company.getMissions()) {
            line = new StringBuilder(m.getId());
            StringBuilder finalLine = new StringBuilder();
            m.getNeed().getCompetenceCurrent().forEach((competence, empList) -> {
                for (Employee e : empList) {
                    finalLine.append(";").append(competence.getId()).append("~").append(e.getId());
                }
            });
            line.append(finalLine);
            appendNewLine(path, line.toString(), true);
        }
    }
}
