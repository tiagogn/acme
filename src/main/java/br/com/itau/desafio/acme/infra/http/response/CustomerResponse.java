package br.com.itau.desafio.acme.infra.http.response;

import br.com.itau.desafio.acme.core.domain.Customer;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CustomerResponse(
        @JsonProperty("document_number")
        String documentNumber,
        String name,
        String type,
        String gender,
        @JsonProperty("date_of_birth")
        String dateOfBirth,
        String email,
        @JsonProperty("phone_number")
        String phoneNumber
        ) {

    public static CustomerResponse fromDomain(Customer customer) {
        return new CustomerResponse(
                customer.getDocumentNumber(),
                customer.getName(),
                customer.getType(),
                customer.getGender().name(),
                customer.getDateOfBirth().toString(),
                customer.getEmail(),
                customer.getPhoneNumber()
        );
    }
}
