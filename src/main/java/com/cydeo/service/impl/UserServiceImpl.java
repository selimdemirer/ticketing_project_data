package com.cydeo.service.impl;

import com.cydeo.dto.ProjectDTO;
import com.cydeo.dto.TaskDTO;
import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.ProjectService;
import com.cydeo.service.TaskService;
import com.cydeo.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, @Lazy TaskService taskService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAllByIsDeletedOrderByFirstNameDesc(false);

        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

        User user = userRepository.findByUserNameAndIsDeleted(username,false);

        return userMapper.convertToDTO(user);

    }

    @Override
    public void save(UserDTO userDTO) {

        userRepository.save(userMapper.convertToEntity(userDTO));

    }

//    @Override
//    public void deleteByUserName(String username) {
//
//        userRepository.deleteByUserName(username);
//
//    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        //Find current user
        User user = userRepository.findByUserNameAndIsDeleted(userDTO.getUserName(),false); //has id
        //Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(userDTO); //has id?
        //Set id to the converted object
        convertedUser.setId(user.getId());
        //Save the updated user in the DB
        userRepository.save(convertedUser);

        return findByUserName(userDTO.getUserName());

    }

    @Override
    public void delete(String username) {

        //go to DB and get that user with username
        User user = userRepository.findByUserNameAndIsDeleted(username,false);

        if (checkIfUserCanBeDeleted(user)) {
            //change the isdeleted field to true
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId()); // harold@manager.com-2
            //save the object in the DB
            userRepository.save(user);
        }

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {

        List<User> users = userRepository.findByRoleDescriptionIgnoreCaseAndIsDeleted(role,false);

        return users.stream().map(userMapper::convertToDTO).collect(Collectors.toList());

    }


    private boolean checkIfUserCanBeDeleted(User user) {//Since this is a private method it cannot connect to Controller, so I want to use entity object and repository (But I can use dto as well)

        switch (user.getRole().getDescription()) {
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.listAllNonCompletedByAssignedManager(userMapper.convertToDTO(user));
                return projectDTOList.size() == 0;

            case "Employee":
                List<TaskDTO> taskDTOList = taskService.listAllNonCompletedByAssignedEmployee(userMapper.convertToDTO(user));
                return taskDTOList.size() == 0;

            default:
                return true;
        }

    }

}
