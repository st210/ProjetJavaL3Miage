package Model;

public class CompetenceParBesoin {
    private Competence competence;
    private int nbEmployes;

    public CompetenceParBesoin(Competence c, int nb) {
        this.competence = c;
        this.nbEmployes = nb;
    }

    // TODO: get le nb de personnes pour chaque competence necessaire
    // TODO: trouver nom plus clair pour la classe
    // TODO: methode toString() à redéfinir
}
