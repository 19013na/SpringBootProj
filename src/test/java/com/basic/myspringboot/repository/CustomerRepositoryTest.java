package com.basic.myspringboot.repository;

import com.basic.myspringboot.entity.Customer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
//@DataJpaTest    // Transactional 포함됨
class CustomerRepositoryTest {

    @Autowired
    CustomerRepository customerRepository;


    @Test
    @Rollback(value = false)
    void testDeleteCustomer(){
        Customer customer = customerRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        customerRepository.delete(customer);
    }


        @Test
    @Rollback(value = false)
    void testUpdateCustomer(){
        Customer customer = customerRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        // 수정하려면 Entity의 setter method를 호출한다. dirty read
        // update customers set customer_id=?, customer_name=? where id=? (entity에 @DynamicUpdate 적용 전)
        // update customers set customer_name=? where id=? (@DynamicUpdate 적용 후)
        customer.setCustomerName("hi");
        //customerRepository.save(customer);
        assertThat(customer.getCustomerName()).isEqualTo("hi");
    }

    @Test
    void testByNotFoundException(){
        // <X extends Throwable> T orElseThrow(Supplier<? extends T> exceptionSupplier)
        // Supplier 의 추상메서드 T get()
        Customer customer = customerRepository.findByCustomerId("A004")
                .orElseThrow(() -> new RuntimeException("Customer Not Found"));
        //assertThat(customer.getCustomerId()).isEqualTo("A001");
    }

    @Test
    //@Disabled
    void testFindBy() {
        Optional<Customer> optionalCustomer = customerRepository.findById(1L); // ctrl + alt + v

        //assertThat(optionalCustomer).isNotEmpty()
        if (optionalCustomer.isPresent()) {
            Customer existCustomer = optionalCustomer.get();
            assertThat(existCustomer.getId()).isEqualTo(1L);
        }

        // Optional 의 orElseGet(Supplier<? extends T> supplier)
        // Supplier 의 추상메서드 T get()
        Optional<Customer> optionalCustomer2 = customerRepository.findByCustomerId("A001");
        Customer a001customer = optionalCustomer2.orElseGet(() -> new Customer());
        assertThat(a001customer.getCustomerName()).isEqualTo("스프링1");

        Customer notFoundCustomer = customerRepository.findByCustomerId("A004")
                .orElseGet(() -> new Customer());
        assertThat(notFoundCustomer.getCustomerName()).isNull();
    }



    @Test
    @Rollback(value = false)    // rollback 시키지 않기 설정
    @Disabled
    void testCreateCustomer(){
         // Given (준비 단계)
        Customer customer = new Customer();
        customer.setCustomerId("A001");
        customer.setCustomerName("스프링1");

        // When (실행)
        Customer addCustomer = customerRepository.save(customer);

        // Then (검증)
        assertThat(addCustomer).isNotNull();
        assertThat(addCustomer.getCustomerName()).isEqualTo("스프링1");
    }
}