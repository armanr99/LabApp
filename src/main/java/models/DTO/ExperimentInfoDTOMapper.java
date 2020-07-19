package main.java.models.DTO;

import main.java.models.Experiment.ExperimentInfo;
import main.java.models.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class ExperimentInfoDTOMapper {
    public ExperimentInfo getExperimentInfo(ExperimentInfoDTO experimentInfoDTO) {
        return Storage.getInstance().getExperimentInfoContainer().find(experimentInfoDTO.getId());
    }

    public List<ExperimentInfo> getExperimentInfos(List<ExperimentInfoDTO> experimentInfoDTOs) {
        List<ExperimentInfo> experimentInfos = new ArrayList<>();

        for (ExperimentInfoDTO ExperimentInfoDTO : experimentInfoDTOs) {
            experimentInfos.add(getExperimentInfo(ExperimentInfoDTO));
        }

        return experimentInfos;
    }

    public ExperimentInfoDTO getExperimentInfoDTO(ExperimentInfo experimentInfo) {
        return new ExperimentInfoDTO(experimentInfo);
    }

    public List<ExperimentInfoDTO> getExperimentInfoDTOs(List<ExperimentInfo> experimentInfos) {
        List<ExperimentInfoDTO> experimentInfoDTOs = new ArrayList<>();

        for (ExperimentInfo ExperimentInfo : experimentInfos) {
            experimentInfoDTOs.add(getExperimentInfoDTO(ExperimentInfo));
        }

        return experimentInfoDTOs;
    }
}
