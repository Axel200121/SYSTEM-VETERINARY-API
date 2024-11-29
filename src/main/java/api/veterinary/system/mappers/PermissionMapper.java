package api.veterinary.system.mappers;

import api.veterinary.system.dtos.PermissionDTO;
import api.veterinary.system.entities.Permission;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    @Autowired
    private ModelMapper modelMapper;

    public PermissionDTO convertPermissionToDTO(Permission permission){
        return modelMapper.map(permission, PermissionDTO.class);
    }

    public Permission convertPermissionToEntity(PermissionDTO permissionDTO){
        return modelMapper.map(permissionDTO, Permission.class);
    }
}
