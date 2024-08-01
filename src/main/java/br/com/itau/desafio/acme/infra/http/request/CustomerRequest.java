package br.com.itau.desafio.acme.infra.http.request;

import br.com.itau.desafio.acme.core.domain.Customer;
import br.com.itau.desafio.acme.core.domain.Gender;

import java.time.LocalDate;

public record CustomerRequest(
        String document_number,
        String name,
        String type,
        String gender,
        LocalDate date_of_birth,
        String email,
        String phone_number
){
    public Customer toDomain() {
        return new Customer(document_number, name, type,
                Gender.valueOf(this.gender), date_of_birth, email, phone_number);
    }
}
