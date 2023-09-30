package com.cydeo.service.impl;

import com.cydeo.dto.RoleDTO;
import com.cydeo.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    //controller called me and requesting all RoleDTOs so it can show in the drop-down in the UI
    //I need to make a call to DB and get all the roles from table
    //go to repository and find a service(method) which gives me the roles from DB
    //how will I call any service here?
    @Override
    public List<RoleDTO> listAllRoles() {

        return null;
    }

    @Override
    public RoleDTO findById(Long id) {
        return null;
    }
}
