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

    void rewriteFile(String file, String content) throws IOException {
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
}
