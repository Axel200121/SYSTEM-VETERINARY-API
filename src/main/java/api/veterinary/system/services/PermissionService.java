package api.veterinary.system.services;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.PermissionDTO;
import api.veterinary.system.entities.Permission;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface PermissionService {
    ApiResponseDTO savePermission(PermissionDTO permissionDTO, BindingResult bindingResult);
    ApiResponseDTO updatePermission(String idPermission, PermissionDTO permissionDTO, BindingResult bindingResult);
    ApiResponseDTO getAllPermission(Pageable pageable);
    ApiResponseDTO getPermission(String idPermission);
    ApiResponseDTO deletePermission(String idPermission);
    Permission getPermissionById(String idPermission);
    List<Permission> getPermissionList(List<PermissionDTO> permissionDTOList);
}
