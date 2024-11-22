package api.veterinary.system.services.impl;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.SpecialtyDTO;
import api.veterinary.system.dtos.ValidateFieldDTO;
import api.veterinary.system.entities.Specialty;
import api.veterinary.system.mappers.SpecialtyMapper;
import api.veterinary.system.repositories.SpecialtyRepository;
import api.veterinary.system.services.SpecialtyService;
import api.veterinary.system.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpecialtyServiceImpl.class);

    @Autowired
    private SpecialtyMapper specialtyMapper;

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public ApiResponseDTO saveSpecialty(SpecialtyDTO specialtyDTO, BindingResult bindingResult) {

        List<ValidateFieldDTO> inpustValidate = validateInputs(bindingResult);
        if (!inpustValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.INVALID_INPUTS.getValue(),inpustValidate);

        Optional<Specialty> specialty = specialtyRepository.findByName(specialtyDTO.getName());
        if (specialty.isPresent())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_EXISTS.getValue(),null);

        Specialty saveSpecialty = specialtyRepository.save(specialtyMapper.convertSpecialtyToEntity(specialtyDTO));

        return new ApiResponseDTO(HttpStatus.CREATED.value(),Constants.REGISTER_CREATED.getValue(),specialtyMapper.convertSpecialtyToDTO(saveSpecialty));
    }

    @Override
    public ApiResponseDTO updateSpecialty(String idSpecialty, SpecialtyDTO specialtyDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> inputsValidate = validateInputs(bindingResult);
        if (!inputsValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.INVALID_INPUTS.getValue(),inputsValidate);

        Specialty specialtyBD = getSpecialtyById(idSpecialty);
        if (specialtyBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        specialtyBD.setName(specialtyDTO.getName());
        specialtyBD.setDescription(specialtyDTO.getDescription());
        Specialty editSpecialty = specialtyRepository.save(specialtyBD);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_UPDATE.getValue(),specialtyMapper.convertSpecialtyToDTO(editSpecialty));
    }

    @Override
    public ApiResponseDTO getAllSpecialty() {
        List<Specialty> specialtyList = specialtyRepository.findAll();

        if (specialtyList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.LIST_EMPTY.getValue(),null);

        List<SpecialtyDTO> specialtyDTOList = specialtyList.stream().map(specialtyMapper::convertSpecialtyToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.LIST_NOT_EMPTY.getValue(),specialtyDTOList);
    }

    @Override
    public ApiResponseDTO getSpecialty(String idSpecialty) {
        Specialty specialtyBD = getSpecialtyById(idSpecialty);
        if (specialtyBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_INFORMATION.getValue(),specialtyMapper.convertSpecialtyToDTO(specialtyBD));
    }

    @Override
    public ApiResponseDTO deleteSpecialty(String idSpecialty) {
        Specialty specialtyBD = getSpecialtyById(idSpecialty);
        if (specialtyBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        specialtyRepository.deleteById(idSpecialty);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_DELETE.getValue(),null);
    }

    @Override
    public Specialty getSpecialtyById(String idSpecialty) {
        Optional<Specialty> specialtyBD = specialtyRepository.findById(idSpecialty);
        return specialtyBD.orElse(null);
    }


    private List<ValidateFieldDTO> validateInputs(BindingResult bindingResult){
        List<ValidateFieldDTO> validateFieldDTOList = new ArrayList<>();
        if (bindingResult.hasErrors()){
            bindingResult.getFieldErrors().forEach(fieldError -> {
                ValidateFieldDTO validateFieldDTO = new ValidateFieldDTO();
                validateFieldDTO.setFieldValidated(fieldError.getField());
                validateFieldDTO.setFieldValidatedMessage(fieldError.getDefaultMessage());
                validateFieldDTOList.add(validateFieldDTO);
            });
        }
        return validateFieldDTOList;
    }
}
