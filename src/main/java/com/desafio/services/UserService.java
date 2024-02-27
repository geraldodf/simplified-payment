package com.desafio.services;

import com.desafio.dtos.UpdateUserDTO;
import com.desafio.models.User;
import com.desafio.repositories.UserRepository;
import static  io.micrometer.common.util.StringUtils.isNotBlank;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ArrayList<User> getAll() {
        try {
            return (ArrayList<User>) this.userRepository.findAll();
        } catch (Exception e) {
            throw new IllegalArgumentException("Erro ao buscar usuários");
        }

    }

    public User getOneById(Long id) {
        try {
            return this.userRepository.findById(id).get();
        } catch (Exception e) {
            throw new IllegalArgumentException("Usuário inexistente");
        }
    }

    public User create(User user) {
        try {
            return this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Problema ao criar usuário");
        }
    }

    public User update(Long id, UpdateUserDTO userDTO) {
        User user = this.getOneById(id);
        if (userDTO.getName() != null & isNotBlank(userDTO.getName())) {
            user.setName(userDTO.getName());
        } else {
            throw new IllegalArgumentException("Nome nulo ou vazio!");
        }
        return this.userRepository.save(user);
    }

    public void delete(Long id) {
        try {
            this.userRepository.delete(this.getOneById(id));
        } catch (Exception e) {
            throw new IllegalArgumentException("Usuário inexistente");
        }
    }
}
