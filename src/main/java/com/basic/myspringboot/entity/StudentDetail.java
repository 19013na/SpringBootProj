package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

//StudentDetail 클래스
// Owner(주인) - FK(외래키)를 가진 쪽이 주인
@Entity
@Table(name = "student_details")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class StudentDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    
    @Column(nullable = false)
    private String address;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column
    private String email;
    
    @Column
    private LocalDate dateOfBirth;

    // OneToOne - 1:1 지연로딩
    @OneToOne(fetch = FetchType.LAZY)   // fetch의 default값이 EAGER임
    // @JoinColumn : FK(외래키)에 해당하는 어노테이션
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}
