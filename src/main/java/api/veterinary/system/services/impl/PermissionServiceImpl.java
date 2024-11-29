package api.veterinary.system.services.impl;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.PermissionDTO;
import api.veterinary.system.dtos.RoleDTO;
import api.veterinary.system.dtos.ValidateFieldDTO;
import api.veterinary.system.entities.Permission;
import api.veterinary.system.entities.Role;
import api.veterinary.system.mappers.PermissionMapper;
import api.veterinary.system.repositories.PermissionRepository;
import api.veterinary.system.services.PermissionService;
import api.veterinary.system.utils.Constants;
import api.veterinary.system.utils.StatusRegister;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PermissionServiceImpl  implements PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public ApiResponseDTO savePermission(PermissionDTO permissionDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> inpustValidate = this.validateInputs(bindingResult);
        if (!inpustValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.INVALID_INPUTS.getValue(),inpustValidate);

        Permission permission = this.permissionRepository.save(permissionMapper.convertPermissionToEntity(permissionDTO));
        return new ApiResponseDTO(HttpStatus.CREATED.value(),Constants.REGISTER_CREATED.getValue(),this.permissionMapper.convertPermissionToDTO(permission));
    }

    @Override
    public ApiResponseDTO updatePermission(String idPermission, PermissionDTO permissionDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> inpustValidate = this.validateInputs(bindingResult);
        if (!inpustValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.INVALID_INPUTS.getValue(),inpustValidate);

        Permission permissionBD = getPermissionById(idPermission);
        if (permissionBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), Constants.REGISTER_NOT_EXISTS.getValue(),null);

        permissionBD.setName(permissionDTO.getName());
        permissionBD.setDescription(permissionDTO.getDescription());
        permissionBD.setStatus(StatusRegister.valueOf(permissionDTO.getStatus()));
        Permission permissionEdit = this.permissionRepository.save(permissionBD);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_UPDATE.getValue(),this.permissionMapper.convertPermissionToDTO(permissionEdit));
    }

    @Override
    public ApiResponseDTO getAllPermission(Pageable pageable) {
        Page<Permission> permissionsBD = permissionRepository.findAll(pageable);

        if(permissionsBD.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.LIST_EMPTY.getValue(), null);

        List<PermissionDTO> permissionDTOList = permissionsBD.stream().map(permissionMapper::convertPermissionToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.LIST_NOT_EMPTY.getValue(),permissionDTOList);
    }

    @Override
    public ApiResponseDTO getPermission(String idPermission) {
        Permission permissionBD = getPermissionById(idPermission);
        if (permissionBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), Constants.REGISTER_NOT_EXISTS.getValue(),null);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_INFORMATION.getValue(),permissionMapper.convertPermissionToDTO(permissionBD));
    }

    @Override
    public ApiResponseDTO deletePermission(String idPermission) {
        Permission permissionBD = getPermissionById(idPermission);
        if (permissionBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), Constants.REGISTER_NOT_EXISTS.getValue(),null);

        this.permissionRepository.deleteById(idPermission);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_DELETE.getValue(),null);
    }

    @Override
    public List<Permission> getPermissionList(List<PermissionDTO> permissionDTOList) {
        if (permissionDTOList.isEmpty()) return null;
        List<Permission> assigmentPermissionList = new ArrayList<>();
        for (PermissionDTO permisison : permissionDTOList){
            Permission getPermission = getPermissionById(permisison.getId());
            assigmentPermissionList.add(getPermission);
        }
        return assigmentPermissionList;
    }

    @Override
    public Permission getPermissionById(String idPermission) {
        Optional<Permission> permissionBD = permissionRepository.findById(idPermission);
        return permissionBD.orElse(null);
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
