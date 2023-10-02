package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {

        List<User> userList = userRepository.findAll(Sort.by("firstName"));

        return userList.stream().map(userMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {

       User user = userRepository.findByUserName(username);

       return userMapper.convertToDTO(user);

    }

    @Override
    public void save(UserDTO user) {

        userRepository.save(userMapper.convertToEntity(user));

    }

    @Override
    public void deleteByUserName(String username) {

        userRepository.deleteByUserName(username);

    }

    @Override
    public UserDTO update(UserDTO userDTO) {

        //Find current user
        User user = userRepository.findByUserName(userDTO.getUserName()); //has id
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



    }
}
