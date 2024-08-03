package br.com.itau.desafio.acme.infra.http;

import br.com.itau.desafio.acme.core.application.GetInsuranceQuote;
import br.com.itau.desafio.acme.core.application.ReceivedInsuranceQuote;
import br.com.itau.desafio.acme.core.domain.InsuranceQuote;
import br.com.itau.desafio.acme.infra.http.request.InsuranceQuoteRequest;
import br.com.itau.desafio.acme.infra.http.response.InsuranceQuoteResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/insurance-quote")
@Slf4j
public class InsuranceQuoteController {

    private final ReceivedInsuranceQuote receivedInsuranceQuote;

    private final GetInsuranceQuote getInsuranceQuote;

    public InsuranceQuoteController(
            ReceivedInsuranceQuote receivedInsuranceQuote,
            GetInsuranceQuote getInsuranceQuote
    ) {
        this.receivedInsuranceQuote = receivedInsuranceQuote;
        this.getInsuranceQuote = getInsuranceQuote;
    }

    @PostMapping
    public ResponseEntity<InsuranceQuoteResponse> receiveInsuranceQuote(@RequestBody(required = true) InsuranceQuoteRequest insuranceQuoteRequest) {
        log.info("Received insurance quote: {}", insuranceQuoteRequest);
        InsuranceQuote insuranceQuote = insuranceQuoteRequest.toDomain();
        receivedInsuranceQuote.execute(insuranceQuote);
        return ResponseEntity.ok(InsuranceQuoteResponse.fromDomain(insuranceQuote));
    }

    @GetMapping("{id}")
    public ResponseEntity<InsuranceQuoteResponse> getInsuranceQuote(@PathVariable(required = true) Integer id) {
        log.info("Get insurance quote by id: {}", id);
        InsuranceQuote insuranceQuote = getInsuranceQuote.getById(id);
        if (insuranceQuote == null)
            return ResponseEntity.notFound().build();
        else
            return ResponseEntity.ok(InsuranceQuoteResponse.fromDomain(insuranceQuote));
    }
}
