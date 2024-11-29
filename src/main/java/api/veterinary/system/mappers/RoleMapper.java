package api.veterinary.system.mappers;

import api.veterinary.system.dtos.RoleDTO;
import api.veterinary.system.entities.Role;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    @Autowired
    private ModelMapper modelMapper;

    public RoleDTO convertRoleToDTO(Role role){
        return modelMapper.map(role, RoleDTO.class);
    }

    public Role convertRoleToEntity(RoleDTO roleDTO){
        return modelMapper.map(roleDTO,Role.class);
    }
}
