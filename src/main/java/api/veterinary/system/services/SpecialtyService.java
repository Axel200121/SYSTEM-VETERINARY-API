package api.veterinary.system.services;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.SpecialtyDTO;
import api.veterinary.system.entities.Specialty;
import org.springframework.validation.BindingResult;

public interface SpecialtyService {
    ApiResponseDTO saveSpecialty(SpecialtyDTO specialtyDTO, BindingResult bindingResult);
    ApiResponseDTO updateSpecialty(String idSpecialty, SpecialtyDTO specialtyDTO, BindingResult bindingResult);
    ApiResponseDTO getAllSpecialty();
    ApiResponseDTO getSpecialty(String idSpecialty);
    ApiResponseDTO deleteSpecialty(String idSpecialty);
    Specialty getSpecialtyById(String idSpecialty);
}
