package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    private UUID id;

    private String documentNumber;

    private String name;

    private String type;

    private Gender gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;
}
