package main.java.models.DTO;

import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class LabDTOMapper {
    public Lab getLab(LabDTO labDTO) {
        return Storage.getInstance().getLabContainer().find(labDTO.getId());
    }

    public List<Lab> getLabs(List<LabDTO> labDTOs) {
        List<Lab> labs = new ArrayList<>();

        for (LabDTO labDTO : labDTOs) {
            labs.add(getLab(labDTO));
        }

        return labs;
    }

    public LabDTO getLabDTO(Lab lab) {
        return new LabDTO(lab);
    }

    public List<LabDTO> getLabDTOs(List<Lab> labs) {
        List<LabDTO> labDTOs = new ArrayList<>();

        for (Lab lab : labs) {
            labDTOs.add(getLabDTO(lab));
        }

        return labDTOs;
    }
}
