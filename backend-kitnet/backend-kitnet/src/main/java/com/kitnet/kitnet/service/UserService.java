package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.exception.EmailAlreadyInUseException;
import com.kitnet.kitnet.exception.InvalidCredentialsException;
import com.kitnet.kitnet.exception.PasswordMismatchException;
import com.kitnet.kitnet.exception.UserNotFoundException;

public interface UserService {
    User register(UserRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException;
    User login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException;
}
