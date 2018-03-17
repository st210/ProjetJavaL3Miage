package Model;

public class Competence {
    private String id;
    private String libelleFR;
    private String libelleEN;

    public Competence(String id, String libelleEN, String libelleFR) {
        this.id = id;
        this.libelleFR = libelleFR;
        this.libelleEN = libelleEN;
    }

    // à voir plus tard pour n'avoir qu'un seul libelle et gerer via l'interface la langue par un fichier de config (+thymeleaf?)
    public Competence(String libF, String libE) {
        this.libelleEN = libE;
        this.libelleFR = libF;
    }

    /***********
     * GETTERS *
     ***********/

    public String getId() {
        return id;
    }

    public String getLibelleFR() {
        return libelleFR;
    }

    public String getLibelleEN() {
        return libelleEN;
    }

    /***********
     * SETTERS *
     ***********/

    // TODO: get/set une competence
    // TODO: methode toString() à redéfinir
}
