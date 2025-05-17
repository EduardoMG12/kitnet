package com.kitnet.kitnet.service;

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User register(UserRegisterDTO dto) throws Exception {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new Exception("As senhas não coincidem.");
        }

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new Exception("E-mail já está em uso.");
        }

        User user = new User();
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setPhone(dto.getPhone());
        user.setPassword(dto.getPassword());
        user.setAcceptTerms(dto.getAcceptTerms());
        user.setCpf(dto.getCpf());
        user.setDocumentImageWithUser(dto.getDocumentImageWithUser());

        return userRepository.save(user);
    }

    public User login(UserLoginDTO dto) throws Exception {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Usuário não encontrado."));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new Exception("Senha incorreta.");
        }

        return user;
    }
}
