package api.veterinary.system.services;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.RoleDTO;
import api.veterinary.system.entities.Role;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

public interface RoleService {
    ApiResponseDTO saveRole(RoleDTO roleDTO, BindingResult bindingResult);
    ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO, BindingResult bindingResult);
    ApiResponseDTO getAllRole(Pageable pageable);
    ApiResponseDTO getRole(String idRole);
    ApiResponseDTO deleteRole(String idRole);
    Role getRoleById(String idRole);
}
