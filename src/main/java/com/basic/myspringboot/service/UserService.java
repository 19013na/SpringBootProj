package com.basic.myspringboot.service;

import com.basic.myspringboot.controller.dto.UserDTO;
import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true) // 성능을 위해 readOnly라고 했지만, db data를 수정해야하는 method는 readonly가 아니기 때문에 각 메소드에 따로 transactional을 설정해줌
public class UserService {
    private final UserRepository userRepository;

    // 등록
    @Transactional
    public UserDTO.UserResponse createUser(UserDTO.UserCreateRequest request) {
        // Email 중복 검사
        userRepository.findByEmail(request.getEmail()) // Optional<User>
                .ifPresent(
                        user -> {
                    throw new BusinessException("User with this Email already exist", HttpStatus.CONFLICT);});

        User user = request.toEntity();
        User savedUser = userRepository.save(user);
        return new UserDTO.UserResponse(savedUser);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // 수정
    @Transactional
    public UserDTO.UserResponse updateUserByEmail(String email, UserDTO.UserUpdateRequest userDetail) {
        User user = getUserByEmail(email);
        //dirty read
        user.setName(userDetail.getName());
        // transactional 사용하면 update는 userRepository에 직접 저장하지 않아도 됨
        //return userRepository.save(user);
        return new UserDTO.UserResponse(user);
    }

    // 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }
}