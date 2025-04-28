package com.todoapp.application.ds_gateways;


import java.util.Map;

import com.todoapp.application.ds_gateways.dto.UserDataRequestDTO;
import com.todoapp.application.ds_gateways.dto.UserDataResponseDTO;

public interface UserDsGateway {
    Map<String,String> getUserWithUsername(String username);

    UserDataResponseDTO saveUser(UserDataRequestDTO dtoUser);
    
    boolean existUserByEmail(String email);

    UserDataResponseDTO getUserWithEmail(String email);

    Map<String, String> loadUserByEmail(String email);

    UserDataResponseDTO getUserWithUserId(String userId);
}
