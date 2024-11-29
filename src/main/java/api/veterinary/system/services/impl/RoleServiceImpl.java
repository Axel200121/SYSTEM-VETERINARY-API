package api.veterinary.system.services.impl;

import api.veterinary.system.dtos.ApiResponseDTO;
import api.veterinary.system.dtos.RoleDTO;
import api.veterinary.system.dtos.ValidateFieldDTO;
import api.veterinary.system.entities.Permission;
import api.veterinary.system.entities.Role;
import api.veterinary.system.mappers.RoleMapper;
import api.veterinary.system.repositories.RoleRepository;
import api.veterinary.system.services.PermissionService;
import api.veterinary.system.services.RoleService;
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
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionService permissionService;

    @Override
    public ApiResponseDTO saveRole(RoleDTO roleDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> inputsValidate = this.validateInputs(bindingResult);
        if (!inputsValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_INPUTS.getValue(),inputsValidate);

        Optional<Role> roleBD = roleRepository.findByName(roleDTO.getName());
        if (roleBD.isPresent())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_EXISTS.getValue(),null);

        List<Permission> assigmentPermissionList = this.permissionService.getPermissionList(roleDTO.getPermissions());
        if (assigmentPermissionList == null || assigmentPermissionList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.LIST_PERMISSIONS_NOT_EXIST.getValue(),null);

        Role role = roleMapper.convertRoleToEntity(roleDTO);
        role.setPermissions(assigmentPermissionList);
        Role roleSave = roleRepository.save(role);
        return new ApiResponseDTO(HttpStatus.CREATED.value(),Constants.REGISTER_CREATED.getValue(),roleMapper.convertRoleToDTO(roleSave));
    }

    @Override
    public ApiResponseDTO updateRole(String idRole, RoleDTO roleDTO, BindingResult bindingResult) {
        List<ValidateFieldDTO> inputsValidate = this.validateInputs(bindingResult);
        if (!inputsValidate.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(), Constants.INVALID_INPUTS.getValue(),inputsValidate);

        Role roleBD = this.getRoleById(idRole);
        if (roleBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        List<Permission> assigmentPermissionList = this.permissionService.getPermissionList(roleDTO.getPermissions());
        if (assigmentPermissionList == null || assigmentPermissionList.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.LIST_PERMISSIONS_NOT_EXIST.getValue(),null);


        roleBD.setName(roleDTO.getName());
        roleBD.setDescription(roleDTO.getDescription());
        roleBD.setStatus(StatusRegister.valueOf(roleDTO.getStatus()));
        roleBD.setPermissions(assigmentPermissionList);
        Role roleEdit = roleRepository.save(roleBD);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_UPDATE.getValue(),roleMapper.convertRoleToDTO(roleEdit));
    }

    @Override
    public ApiResponseDTO getAllRole(Pageable pageable) {
        Page<Role> rolesBD = roleRepository.findAll(pageable);

        if(rolesBD.isEmpty())
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.LIST_EMPTY.getValue(), null);

        List<RoleDTO> roleDTOList = rolesBD.stream().map(roleMapper::convertRoleToDTO).collect(Collectors.toList());
        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.LIST_NOT_EMPTY.getValue(),roleDTOList);
    }

    @Override
    public ApiResponseDTO getRole(String idRole) {
        Role roleBD = this.getRoleById(idRole);
        if (roleBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_INFORMATION.getValue(),roleMapper.convertRoleToDTO(roleBD));
    }

    @Override
    public ApiResponseDTO deleteRole(String idRole) {
        Role roleBD = this.getRoleById(idRole);
        if (roleBD == null)
            return new ApiResponseDTO(HttpStatus.BAD_REQUEST.value(),Constants.REGISTER_NOT_EXISTS.getValue(),null);

        roleRepository.deleteById(idRole);

        return new ApiResponseDTO(HttpStatus.OK.value(),Constants.REGISTER_DELETE.getValue(),null);
    }

    @Override
    public Role getRoleById(String idRole) {
        Optional<Role> role = roleRepository.findById(idRole);
        return role.orElse(null);
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
