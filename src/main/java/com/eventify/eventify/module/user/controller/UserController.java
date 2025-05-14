package com.eventify.eventify.module.user.controller;

import com.eventify.eventify.exceptions.EntityAlreadyExistsException;
import com.eventify.eventify.exceptions.ResourceNotFoundException;
import com.eventify.eventify.module.user.model.dto.UserUpdateDTO;
import com.eventify.eventify.module.user.model.entity.UserEntity;
import com.eventify.eventify.module.user.model.entity.UserWalletEntity;
import com.eventify.eventify.module.user.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RelationServiceNotRegisteredException;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<UserEntity> createUser(@Valid @RequestBody UserEntity userEntity) {
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

        return ResponseEntity.ok().body(this.userRepository.save(userEntity));
    }

    @GetMapping("/{cpf}")
    public UserEntity getUser(@PathVariable String cpf) {
        return this.userRepository.findByCpf(cpf).orElseThrow(() ->
            new ResourceNotFoundException("Cpf não encontrado")
        );
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<UserEntity> updateUser(@Valid @PathVariable String cpf, @RequestBody UserUpdateDTO userUpdateDTO) {
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
        return ResponseEntity.ok().body(user);
    }

    @DeleteMapping("/{cpf}")
    public void deleteUser(@PathVariable String cpf) {
        UserEntity user = this.userRepository.findByCpf(cpf).orElseThrow(() ->
            new ResourceNotFoundException("Cpf não encontrado")
        );

        this.userRepository.delete(user);
    }
}
