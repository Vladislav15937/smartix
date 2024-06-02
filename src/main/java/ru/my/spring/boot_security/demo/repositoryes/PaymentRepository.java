package ru.my.spring.boot_security.demo.repositoryes;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.my.spring.boot_security.demo.entity.Payments;

public interface PaymentRepository extends PagingAndSortingRepository<Payments, Long>,
        JpaRepository<Payments, Long> {

    Page<Payments> findAllByUserId(Long userId, Pageable pageable);
}
