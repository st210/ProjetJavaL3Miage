package Model;

public enum Statut {
    PREP ("en preparation"),
    PLANIF ("planifiee"),
    COURS ("en cours"),
    FIN ("terminee");

    private String stat;

    Statut(String s) {
        this.stat = s;
    }

    @Override
    public String toString(){
        return this.stat;
    }
}
