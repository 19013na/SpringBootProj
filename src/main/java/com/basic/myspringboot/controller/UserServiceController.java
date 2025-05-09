package com.basic.myspringboot.controller;

import com.basic.myspringboot.controller.dto.UserDTO;
import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserServiceController {
    private final UserService userService;

    // 등록
    @PostMapping
    public ResponseEntity<UserDTO.UserResponse> create(@Valid @RequestBody UserDTO.UserCreateRequest request) {
        UserDTO.UserResponse createdUser = userService.createUser(request);
        return ResponseEntity.ok(createdUser);
    }

    // 전체 목록 조회
    @GetMapping
    public List<UserDTO.UserResponse> getUsers() {
        return userService.getAllUsers()
                .stream()
                .map(UserDTO.UserResponse::new)
                .toList();
                //.map(user -> new UserDTO.UserResponse(user))
    }

    // 조회
    @GetMapping("/{id}")
    public UserDTO.UserResponse getUserById(@PathVariable Long id){
        User existUser = userService.getUserById(id);
        return new UserDTO.UserResponse(existUser);
    }

    @GetMapping("/email/{email}/")
    public UserDTO.UserResponse getUserByEmail(@PathVariable String email){
        return new UserDTO.UserResponse(userService.getUserByEmail(email));
    }

    // 수정
    @PatchMapping("/{email}")
    public ResponseEntity<UserDTO.UserResponse> updateUser(@PathVariable String email, @Valid @RequestBody UserDTO.UserUpdateRequest userDetail){
        UserDTO.UserResponse updatedUser = userService.updateUserByEmail(email, userDetail);
        return ResponseEntity.ok(updatedUser);
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}

