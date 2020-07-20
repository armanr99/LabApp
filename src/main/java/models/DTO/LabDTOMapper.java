package main.java.models.DTO;

import main.java.models.Lab.Lab;
import main.java.models.Storage.Storage;

import java.util.ArrayList;
import java.util.List;

public class LabDTOMapper {
    private Storage storage;

    public LabDTOMapper() {
        storage = Storage.getInstance();
    }

    public Lab getLab(LabDTO labDTO) {
        return storage.getLabRepository().find(labDTO.getId());
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
