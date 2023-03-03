package kr.ac.kopo.polytable.customer.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByPhone(String phone);
}
