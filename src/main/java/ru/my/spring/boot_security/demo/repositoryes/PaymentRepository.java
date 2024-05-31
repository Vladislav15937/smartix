package ru.my.spring.boot_security.demo.repositoryes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.my.spring.boot_security.demo.entity.Payments;

public interface PaymentRepository extends JpaRepository<Payments, Long> {

    Page<Payments> findAll(Pageable pageable);
}
