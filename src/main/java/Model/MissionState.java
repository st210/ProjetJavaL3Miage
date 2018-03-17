package Model;

public enum MissionState {
    PREPARATION ("IN PREPARATION"),
    SCHEDULED ("SCHEDULED"),
    PROGRESS ("IN PROGRESS"),
    COMPLETED ("COMPLETED");

    private String stat;

    MissionState(String s) {
        this.stat = s;
    }

    @Override
    public String toString(){
        return this.stat;
    }
}
