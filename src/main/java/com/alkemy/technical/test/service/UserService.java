package com.alkemy.technical.test.service;

import com.alkemy.technical.test.entities.Users;
import com.alkemy.technical.test.repository.IUserRepository;
import com.alkemy.technical.test.dtos.requests.UserRequestDTO;
import com.alkemy.technical.test.dtos.responses.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserResponseDTO> getAll(){
        var listUser = userRepository.findByStatus(true);
        var listUserResponse = listUser.stream().map(users -> {
            return new UserResponseDTO(users.getId(),users.getName(),users.getEmail(),users.getStatus());
        }).toList();
        return listUserResponse;
    }

    public UserResponseDTO getUser(Long id){
        var user = userRepository.findById(id);
        return new UserResponseDTO(user.get().getId(), user.get().getName(),user.get().getEmail(),user.get().getStatus());
    }

    public UserResponseDTO save(UserRequestDTO userRequestDTO){
        log.info(userRequestDTO.toString());
        var newUser = Users.builder().build();
        var userSave = Users.builder().build();
        if(userRequestDTO.id() != null){
            var userFind = this.userRepository.findById(userRequestDTO.id()).orElseThrow();
            userFind.setName(userRequestDTO.name());
            userFind.setEmail(userRequestDTO.email());
            userFind.setUpdateAt(LocalDateTime.now());
            userSave = this.userRepository.save(userFind);
        }else{
            newUser.setName(userRequestDTO.name());
            newUser.setEmail(userRequestDTO.email());
            newUser.setPassword(this.passwordEncoder.encode("Temporal2025"));
            newUser.setStatus(true);
            newUser.setCreateAt(LocalDateTime.now());
            newUser.setUpdateAt(LocalDateTime.now());
            userSave = this.userRepository.save(newUser);
        }

        return new UserResponseDTO(userSave.getId(),userSave.getName(),userSave.getEmail(),userSave.getStatus());
    }


    public UserResponseDTO delete(Long id){
        var userFind = this.userRepository.findById(id).orElseThrow();
        userFind.setStatus(false);
        userFind.setUpdateAt(LocalDateTime.now());

        var userSave = this.userRepository.save(userFind);
        return new UserResponseDTO(userSave.getId(),userSave.getName(),userSave.getEmail(),userSave.getStatus());
    }
}
