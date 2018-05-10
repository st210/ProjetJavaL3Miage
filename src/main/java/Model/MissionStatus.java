package Model;

public enum MissionStatus {
    PREPARATION ("IN PREPARATION"),
    SCHEDULED ("SCHEDULED"),
    PROGRESS ("IN PROGRESS"),
    COMPLETED ("COMPLETED");

    private String stat;

    MissionStatus(String s) {
        this.stat = s;
    }

    @Override
    public String toString(){
        return this.stat;
    }
}
