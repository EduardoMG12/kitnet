package com.kitnet.kitnet.service.impl; // Mudar o pacote

import com.kitnet.kitnet.dto.UserLoginDTO;
import com.kitnet.kitnet.dto.UserRegisterDTO;
import com.kitnet.kitnet.model.User;
import com.kitnet.kitnet.repository.UserRepository;
import com.kitnet.kitnet.service.UserService; // Importar a interface
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder; // Adicionado para criptografia de senha

@Service
public class UserServiceImpl implements UserService { // Implementar a interface

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injetar PasswordEncoder

    @Override
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
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // Codificar a senha
        user.setAcceptTerms(dto.getAcceptTerms());
        user.setCpf(dto.getCpf());
        user.setDocumentImageWithUser(dto.getDocumentImageWithUser());

        return userRepository.save(user);
    }

    @Override
    public User login(UserLoginDTO dto) throws Exception {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new Exception("Usuário não encontrado."));

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) { // Comparar senhas codificadas
            throw new Exception("Senha incorreta.");
        }

        return user;
    }

    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) { // Se você usa UserDetails padrão ou seu User implementa
                // Se seu UserDetails é o seu próprio objeto User, você pode fazer um cast
                // Ex: if (principal instanceof User) { return ((User) principal).getId(); }
                // Ou se você usa o UserDetails padrão e o username é o email/ID
                String username = ((UserDetails) principal).getUsername();
                // Você precisaria buscar o usuário pelo username (email) para obter o ID
                User user = userRepository.findByEmail(username).orElse(null);
                return (user != null) ? user.getId() : null;
            }
            // Caso o principal seja apenas uma String (ex: "anonymousUser")
            if (principal instanceof String && "anonymousUser".equals(principal)) {
                return null; // Usuário não autenticado
            }
        }
        return null; // Ninguém autenticado
    }
}
