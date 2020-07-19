package main.java.models.Storage;

public class Storage {
    private static Storage instance;
    private LabRepository labRepository;
    private PatientRepository patientRepository;
    private SamplerRepository samplerRepository;
    private ExperimentInfoRepository experimentInfoRepository;
    private ExperimentRecordRepository experimentRecordRepository;

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    public Storage() {
        labRepository = new LabRepository();
        patientRepository = new PatientRepository();
        samplerRepository = new SamplerRepository();
        experimentInfoRepository = new ExperimentInfoRepository();
        experimentRecordRepository = new ExperimentRecordRepository();
    }

    public LabRepository getLabRepository() {
        return labRepository;
    }

    public PatientRepository getPatientRepository() {
        return patientRepository;
    }

    public ExperimentInfoRepository getExperimentInfoRepository() {
        return experimentInfoRepository;
    }

    public ExperimentRecordRepository getExperimentRecordRepository() {
        return experimentRecordRepository;
    }

    public SamplerRepository getSamplerRepository() {
        return samplerRepository;
    }
}
