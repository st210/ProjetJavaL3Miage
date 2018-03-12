package Model;

public enum EtatMission {
    PREP ("EN PREPARATION"),
    PLANIF ("PLANIFIE"),
    COURS ("EN COURS"),
    FIN ("TERMINE");

    private String stat;

    EtatMission(String s) {
        this.stat = s;
    }

    @Override
    public String toString(){
        return this.stat;
    }
}
