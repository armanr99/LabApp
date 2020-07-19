package main.java.models.Storage;

public class Storage {
    private static Storage instance;
    private LabContainer labContainer;
    private PatientContainer patientContainer;
    private ExperimentInfoContainer experimentInfoContainer;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public Storage() {
        labContainer = new LabContainer();
        patientContainer = new PatientContainer();
        experimentInfoContainer = new ExperimentInfoContainer();
    }

    public LabContainer getLabContainer() {
        return labContainer;
    }

    public PatientContainer getPatientContainer() {
        return patientContainer;
    }

    public ExperimentInfoContainer getExperimentInfoContainer() {
        return experimentInfoContainer;
    }
}
