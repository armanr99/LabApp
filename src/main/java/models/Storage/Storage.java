package main.java.models.Storage;

public class Storage {
    private static Storage instance;
    private LabRepository labRepository;
    private PatientRepository patientRepository;
    private SamplerRepository samplerRepository;
    private ExperimentInfoRepository experimentInfoRepository;
    private PatientExperimentRecordRepository patientExperimentRecordRepository;
    private SamplerExperimentRecordRepository samplerExperimentRecordRepository;
    private LabExperimentRecordRepository labExperimentRecordRepository;

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
        patientExperimentRecordRepository = new PatientExperimentRecordRepository();
        samplerExperimentRecordRepository = new SamplerExperimentRecordRepository();
        labExperimentRecordRepository = new LabExperimentRecordRepository();
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

    public SamplerRepository getSamplerRepository() {
        return samplerRepository;
    }

    public PatientExperimentRecordRepository getPatientExperimentRecordRepository() {
        return patientExperimentRecordRepository;
    }

    public SamplerExperimentRecordRepository getSamplerExperimentRecordRepository() {
        return samplerExperimentRecordRepository;
    }

    public LabExperimentRecordRepository getLabExperimentRecordRepository() {
        return labExperimentRecordRepository;
    }
}
