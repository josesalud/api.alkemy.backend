package com.alkemy.technical.test.controller;

import com.alkemy.technical.test.dtos.requests.UserRequestDTO;
import com.alkemy.technical.test.dtos.responses.UserResponseDTO;
import com.alkemy.technical.test.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getALl(){
        var listUser = this.userService.getAll();
        return ResponseEntity.ok(listUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        var userDTO = this.userService.getUser(id);
        return ResponseEntity.ok(userDTO);
    }

    @PostMapping
    public ResponseEntity<UserResponseDTO> save(@RequestBody  UserRequestDTO userRequestDTO){
        var user = this.userService.save(userRequestDTO);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserResponseDTO> delete(@PathVariable  Long id){
        var user = this.userService.delete(id);
        return ResponseEntity.ok(user);
    }
}
