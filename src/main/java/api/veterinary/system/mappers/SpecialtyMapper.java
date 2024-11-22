package api.veterinary.system.mappers;

import api.veterinary.system.dtos.SpecialtyDTO;
import api.veterinary.system.entities.Specialty;
import api.veterinary.system.utils.ModelMapperConfig;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyMapper {

    @Autowired
    private ModelMapper modelMapper;

    public SpecialtyDTO convertSpecialtyToDTO(Specialty specialty) {
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    public Specialty convertSpecialtyToEntity(SpecialtyDTO specialtyDTO) {
        return modelMapper.map(specialtyDTO, Specialty.class);
    }


}
