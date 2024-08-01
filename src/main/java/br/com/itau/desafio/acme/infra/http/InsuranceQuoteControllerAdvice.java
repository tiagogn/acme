package br.com.itau.desafio.acme.infra.http;

import br.com.itau.desafio.acme.core.exception.InsuranceQuoteException;
import br.com.itau.desafio.acme.infra.http.response.InsuranceQuoteError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class InsuranceQuoteControllerAdvice {

    @ExceptionHandler(InsuranceQuoteException.class)
    public ResponseEntity<InsuranceQuoteError> handleInsuranceQuoteException(InsuranceQuoteException e) {
        log.error("Insurance quote exception: {}", e.getMessage());
        var insuranceQuoteError = new InsuranceQuoteError(e.getMessage());
        return ResponseEntity.badRequest().body(insuranceQuoteError);
    }
}
