package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

// Non-Owner (종속)
@Entity
@Table(name = "students")
@NoArgsConstructor
@AllArgsConstructor
@Builder
//@Data // Data 어노테이션은 사용하지 않는 것이 좋다
@Getter @Setter
public class Student {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;
    
    @Column(nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String studentNumber;

    // 1:1 지연로딩
    // 양방향 Student에서 StudentDetail를 참조할 수 있도록 FK에 해당하는 필드명을 mappedBy에 설정한다.
    // Student와 StudentDetail의 라이프 사이클이 같다.
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "student", cascade = CascadeType.ALL)
    private StudentDetail studentDetail;

    // N:1 관계, Student와 Department 관계에서 N쪽에 해당하는 Student가 Owner이다.
    // FK와 매핑되는 필드
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="department_id")
    private Department department;
}