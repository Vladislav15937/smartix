package ru.my.spring.boot_security.demo.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class PaymentDto {

    private String login;

    private Double requiredQuantity;
}
