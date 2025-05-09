package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable= false)
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Column(unique = true, nullable= false)
    @NotBlank(message = "Email is mandatory")
    @Email()  // email 형식 체크
    private String email;
    
    @Column(nullable= false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt= LocalDateTime.now();
}
