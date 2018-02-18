package Model;

public class Competence {
    private int id;
    private String nom;
    private String description;

    public Competence(String nomC) {
        this.id = 0;
        this.nom = nomC;
        this.description = "";
    }

    public Competence(String nomC, String descri) {
        this.id = 0;
        this.nom = nomC;
        this.description = descri;
    }

    // TODO: get/set une competence
    // TODO: methode toString() à redéfinir
}
