package br.com.itau.desafio.acme.infra.http;

import br.com.itau.desafio.acme.core.dto.InsurancePolicyIssued;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/insurance-policy")
public class InsurancePolicyController {

    private final RabbitTemplate rabbitTemplate;

    @Value("${app.rabbit.insurance-policy.queue}")
    private String insurancePolicyQueue;

    public InsurancePolicyController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping
    public ResponseEntity<String> updateInsurancePolicy(@RequestBody(required = true) InsurancePolicyIssued insurancePolicyIssued) {
        rabbitTemplate.convertAndSend(insurancePolicyQueue, insurancePolicyIssued);
        return ResponseEntity.ok("Policy sent to Insurance Quote");
    }

}
