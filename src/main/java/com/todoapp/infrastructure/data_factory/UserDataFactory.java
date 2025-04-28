package com.todoapp.infrastructure.data_factory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todoapp.infrastructure.data_mappers.UserDataMapper;
import com.todoapp.infrastructure.data_mappers.enums.Role;
import com.todoapp.application.ds_gateways.UserDsGateway;
import com.todoapp.application.ds_gateways.dto.UserDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;
import com.todoapp.application.exceptions.BaseException;
import com.todoapp.application.exceptions.ErrorMessage;
import com.todoapp.application.exceptions.MessageType;
import com.todoapp.infrastructure.repositories.UserRepository;

@Component
public class UserDataFactory implements UserDsGateway {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String, String> getUserWithUsername(String username) {
        Optional<UserDataMapper> optUser = userRepository.findByUsername(username);

        if (optUser.isPresent()) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("username", optUser.get().getUsername());
            userMap.put("password", optUser.get().getPassword());
            userMap.put("userId", optUser.get().getId().toString());
            userMap.put("email", optUser.get().getEmail());
            userMap.put("role", optUser.get().getRole().toString());
            return userMap;
        } else
            throw new BaseException(new ErrorMessage(username, MessageType.USERNAME_NOT_FOUND), 404);

    }

    @Override
    public boolean existUserByEmail(String email) {
        Optional<UserDataMapper> optUser = userRepository.findByEmail(email);

        return optUser.isPresent();
    }

    @Override
    public UserDataResponseDTO saveUser(UserDataRequestDTO userDataRequestDTO) {
        UserDataMapper userDataMapper = new UserDataMapper();

        BeanUtils.copyProperties(userDataRequestDTO, userDataMapper);

        userDataMapper.setRole(Role.valueOf(userDataRequestDTO.getRole()));

        UserDataMapper dbUserDataMapper = userRepository.save(userDataMapper);

        UserDataResponseDTO userDataResponseDTO = new UserDataResponseDTO();

        BeanUtils.copyProperties(dbUserDataMapper, userDataResponseDTO);

        userDataResponseDTO.setRole(dbUserDataMapper.getRole().name());

        return userDataResponseDTO;
    }

    @Override
    public UserDataResponseDTO getUserWithEmail(String email) {
        Optional<UserDataMapper> optUser = userRepository.findByEmail(email);
        if (optUser.isPresent()) {

            UserDataResponseDTO userDataResponseDTO = new UserDataResponseDTO();

            BeanUtils.copyProperties(optUser.get(), userDataResponseDTO);

            userDataResponseDTO.setRole(optUser.get().getRole().toString());

            return userDataResponseDTO;
        }
        return null;
    }

    @Override
    public UserDataResponseDTO getUserWithUserId(String userId) {
        Optional<UserDataMapper> optUser = userRepository.findById(UUID.fromString(userId));
        if (optUser.isPresent()) {

            UserDataResponseDTO userDataResponseDTO = new UserDataResponseDTO();

            BeanUtils.copyProperties(optUser.get(), userDataResponseDTO);

            userDataResponseDTO.setRole(optUser.get().getRole().toString());

            return userDataResponseDTO;
        }
        return null;
    }

    public Map<String, String> loadUserByEmail(String email) {
        Optional<UserDataMapper> optUser = userRepository.findByEmail(email);

        if (optUser.isPresent()) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("username", optUser.get().getUsername());
            userMap.put("password", optUser.get().getPassword());
            userMap.put("userId", optUser.get().getId().toString());
            userMap.put("role", optUser.get().getRole().toString());
            return userMap;
        } else
            throw new BaseException(new ErrorMessage(email, MessageType.EMAIL_NOT_FOUND), 404);
    }
    
}
