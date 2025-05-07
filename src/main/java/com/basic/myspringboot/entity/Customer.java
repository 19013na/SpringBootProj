package com.basic.myspringboot.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "customers")
@Getter @Setter
@DynamicUpdate
public class Customer {

    // Primary Key, pk값을 hibarnate(persistence provider)가 결정해라
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 중복 허용 x
    // 중복을 허용하지 않고, Null도 허용하지 않음
    @Column(unique = true, nullable = false)
    private String customerId;

    // Null 값을 허용하지 않음
    @Column(nullable = false)
    private String customerName;

    //private String address;
}
