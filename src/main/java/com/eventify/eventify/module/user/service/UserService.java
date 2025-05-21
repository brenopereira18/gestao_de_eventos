package com.eventify.eventify.module.user.service;

import com.eventify.eventify.exceptions.EntityAlreadyExistsException;
import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.user.model.dto.UserUpdateDTO;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import com.eventify.eventify.module.user.model.entity.UserWalletEntity;
import com.eventify.eventify.module.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity registerUser(UserEntity userEntity) {
        this.userRepository.findByCpf(userEntity.getCpf()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("Usuário já esta cadastrado");
        });

        this.userRepository.findByEmail(userEntity.getEmail()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("Usuário já esta cadastrado");
        });

        this.userRepository.findByPhoneNumber(userEntity.getPhoneNumber()).ifPresent(user -> {
            throw new EntityAlreadyExistsException("Usuário já esta cadastrado");
        });
        userEntity.setUserWalletEntity(new UserWalletEntity());
        return userRepository.save(userEntity);
    }

    public UserEntity getUser(String cpf) {
        return this.userRepository.findByCpf(cpf).orElseThrow(() ->
            new ResourceNotFoundException("Cpf não encontrado")
        );
    }

    public UserEntity updateUser(String cpf, UserUpdateDTO userUpdateDTO) {
        UserEntity user = this.userRepository.findByCpf(cpf).orElseThrow(() ->
            new ResourceNotFoundException("Cpf não encontrado")
        );

        if (userUpdateDTO.getEmail() != null && !user.getEmail().equals(userUpdateDTO.getEmail()) ) {
            this.userRepository.findByEmail(userUpdateDTO.getEmail()).ifPresent(u -> {
                throw new EntityAlreadyExistsException("Email já está em uso");
            });
            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getPhoneNumber() != null && !user.getPhoneNumber().equals(userUpdateDTO.getPhoneNumber())) {
            this.userRepository.findByPhoneNumber(userUpdateDTO.getPhoneNumber()).ifPresent(u -> {
                throw new EntityAlreadyExistsException("Número de telefone já está em uso");
            });
            user.setPhoneNumber(userUpdateDTO.getPhoneNumber());
        }

        if (userUpdateDTO.getPassword() != null) {
            user.setPassword(userUpdateDTO.getPassword());
        }

        this.userRepository.save(user);
        return user;
    }

    public void deleteUser(String cpf) {
        UserEntity user = this.userRepository.findByCpf(cpf).orElseThrow(() ->
            new ResourceNotFoundException("Cpf não encontrado")
        );

        this.userRepository.delete(user);
    }
}
