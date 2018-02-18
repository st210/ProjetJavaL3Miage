package Model;

public class Competence {
    private int id;
    private String libelleFR;
    private String libelleEN;

    // à voir plus tard pour n'avoir qu'un seul libelle et gerer via l'interface la langue par un fichier de config (+thymeleaf?)
    public Competence(String libF, String libE) {
        this.id = 0;
        this.libelleFR = libF;
        this.libelleEN = libE;
    }

    // TODO: get/set une competence
    // TODO: methode toString() à redéfinir
}
