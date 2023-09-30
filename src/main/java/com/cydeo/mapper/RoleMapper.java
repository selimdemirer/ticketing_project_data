package com.cydeo.mapper;


import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    private ModelMapper modelMapper;

    public RoleMapper(ModelMapper modalMapper) {
        this.modelMapper = modalMapper;
    }

    public Role convertToEntity(RoleDTO dto){

        return modelMapper.map(dto, Role.class);

    }

    public RoleDTO convertToDTO(Role entity){

        return modelMapper.map(entity,RoleDTO.class);

    }

}
