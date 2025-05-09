package com.basic.myspringboot.controller;


import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// @Controller + @ResponseBody
@RestController
@RequiredArgsConstructor
//lombok, final인 변수를 초기화하는 생성자를 자동으로 생성해주는 역할을 하는 롬복 어노테이션
@RequestMapping("/api/users")
@Profile("test")    // 현재 prod를 사용하고 있는데, 해당 controller를 실행하고 싶지 않아서 profile("test")를 넣어줌
public class UserRestController {
    private final UserRepository userRepository;

    /* Constructor Injection
    public UserRestController(UserRepository userRepository) {
        System.out.println(">>> UserController " + userRepository.getClass().getName());
        this.userRepository = userRepository;
    }*/

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    public List<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        // public <U> Optional<U> map(Function<? super T. ? extends U> mapper)
        // Function의 추상메서드 R apply(T t)
        /*
        ResponseEntity<User> responseEntity = optionalUser.map(user -> ResponseEntity.ok(user))  // Optional<ResponseEntity>
                .orElse(ResponseEntity.notFound().build());
        return responseEntity;
        */
        // 람다식으로 수정
//        return optionalUser.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
        // 오류는 메시지 주기
        return optionalUser.map(ResponseEntity::ok)
                .orElse(new ResponseEntity("User Not Found", HttpStatus.NOT_FOUND));
    }

    // Exception 사용
    @GetMapping("/email/{email}/")
    public User getUserByEmail(@PathVariable String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        final User existUser = getExistUser(optionalUser);
        return existUser;
    }

    // 수정
    // 부분수정이라서 PatchMapping으로
    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetail){
        final User existUser = getExistUser(userRepository.findById(id));
        //setter method 호출
        existUser.setName(userDetail.getName());
//        User updatedUser = userRepository.save(existUser);
//        return ResponseEntity.ok(updatedUser);
        return ResponseEntity.ok(userRepository.save(existUser));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        final User user = getExistUser(userRepository.findById(id));
        userRepository.delete(user);
        //return ResponseEntity.ok().build();
        //return ResponseEntity.noContent().build();  //ResponseEntity<void> 설정일 경우
        return ResponseEntity.ok("User Deleted");   // 결과 메시지를 주고 싶을 경우
    }

    // 공통 함수
    private User getExistUser(Optional<User> optionalUser) {
        User existUser = optionalUser
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        return existUser;
    }
}
