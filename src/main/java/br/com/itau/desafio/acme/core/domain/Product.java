package br.com.itau.desafio.acme.core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private UUID id;

    private String name;

    private LocalDate createAt;

    private boolean active;
}
