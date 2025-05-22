package com.kitnet.kitnet.service.impl;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.model.UserType;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserService;
import com.kitnet.kitnet.exception.EmailAlreadyInUseException;
import com.kitnet.kitnet.exception.InvalidCredentialsException;
import com.kitnet.kitnet.exception.PasswordMismatchException;
import com.kitnet.kitnet.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegisterDTO dto) throws EmailAlreadyInUseException, PasswordMismatchException {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new PasswordMismatchException("As senhas não coincidem.");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new EmailAlreadyInUseException("E-mail já está em uso.");
        }
        if (userRepository.findByCpf(dto.getCpf()).isPresent()) {
            throw new EmailAlreadyInUseException("CPF já está em uso.");
        }

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setAcceptTerms(dto.getAcceptTerms());
        user.setCpf(dto.getCpf());
        user.setDocumentImageWithUser(dto.getDocumentImageWithUser());
        user.setUserType(dto.getUserType());

        return userRepository.save(user);
    }

    @Override
    public User login(UserLoginDTO dto) throws UserNotFoundException, InvalidCredentialsException {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Senha incorreta.");
        }

        return user;
    }

    @Override
    public User findById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário com ID " + id + " não encontrado."));
    }
}
