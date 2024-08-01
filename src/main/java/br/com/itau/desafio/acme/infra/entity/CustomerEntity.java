package br.com.itau.desafio.acme.infra.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String documentNumber;

    private String name;

    private String type;

    private String gender;

    private LocalDate dateOfBirth;

    private String email;

    private String phoneNumber;

    public CustomerEntity(String documentNumber, String name, String type, String gender, LocalDate dateOfBirth, String email, String phoneNumber) {
        this.documentNumber = documentNumber;
        this.name = name;
        this.type = type;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
