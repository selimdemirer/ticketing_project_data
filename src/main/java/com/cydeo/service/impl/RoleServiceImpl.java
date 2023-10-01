package com.cydeo.service.impl;

import com.cydeo.converter.RoleDtoConverter;
import com.cydeo.dto.RoleDTO;
import com.cydeo.entity.Role;
import com.cydeo.mapper.RoleMapper;
import com.cydeo.repository.RoleRepository;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, RoleDtoConverter roleDtoConverter) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    //controller called me and requesting all RoleDTOs so it can show in the drop-down in the UI
    //I need to make a call to DB and get all the roles from table
    //go to repository and find a service(method) which gives me the roles from DB
    //how will I call any service here?
    @Override
    public List<RoleDTO> listAllRoles() {

        List<Role> roleList = roleRepository.findAll(); // derived query is coming from Jpa

        //I have Role entities from DB
        //I need to convert those Role entities to DTOs
        //I need to use ModelMapper
        //I already created a class called RoleMapper and there are methods for me that will make this conversion
        //All I need to do is DI and implement the methods!

        return roleList.stream().map(roleMapper::convertToDTO).collect(Collectors.toList()); //because method converts only one object, but we created a for loop through stream!;
    }

    @Override
    public RoleDTO findById(Long id) {
        return roleMapper.convertToDTO(roleRepository.findById(id).get());
    }
}
