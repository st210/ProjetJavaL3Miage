package Model;

import java.io.*;
import java.util.Objects;

public class EmployeeMgt extends ModifierCSV {
    /**
     * Ajoute une compétence à l'employé dans le fichier CSV
     *
     * @param idEmp L'ID de l'employé
     * @param c     La compétence à ajouter à l'employé
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
}
