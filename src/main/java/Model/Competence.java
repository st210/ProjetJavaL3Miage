package Model;

import java.util.concurrent.atomic.AtomicInteger;

public class Competence {
    private static final AtomicInteger countID = new AtomicInteger(0);
    private int id;
    private String libelleFR;
    private String libelleEN;

    // à voir plus tard pour n'avoir qu'un seul libelle et gerer via l'interface la langue par un fichier de config (+thymeleaf?)
    public Competence(String libF, String libE) {
        this.libelleFR = libF;
        this.libelleEN = libE;
        this.id = countID.incrementAndGet();
    }

    // TODO: get/set une competence
    // TODO: methode toString() à redéfinir
}
