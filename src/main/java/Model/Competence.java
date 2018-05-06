package Model;

import java.util.Objects;

public class Competence {
    private String id;
    private String libelleFR;
    private String libelleEN;

    public Competence(String id, String libelleEN, String libelleFR) {
        this.id = id;
        this.libelleFR = libelleFR;
        this.libelleEN = libelleEN;
    }

    public Competence(String libF, String libE) {
        this.libelleEN = libE;
        this.libelleFR = libF;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Competence) && ((Competence) obj).id.equals(this.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    //***********//
    //  GETTERS  //
    //***********//

    public String getId() {
        return id;
    }

    public String getLibelleFR() {
        return libelleFR;
    }

    public String getLibelleEN() {
        return libelleEN;
    }

    //***********//
    //  SETTERS  //
    //***********//

    //***********//
    //  METHODS  //
    //***********//

    @Override
    public String toString() {
        return "[" + id + "] " + libelleFR;
    }

}
