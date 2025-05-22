package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;

public interface UserService {
    User register(UserRegisterDTO dto) throws Exception;
    User login(UserLoginDTO dto) throws Exception;
}
