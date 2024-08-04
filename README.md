# ACME - Desafio Engenheiro de Software

![Java](https://img.shields.io/badge/Java-DDD?logo=openjdk&logoColor=black&style=for-the-badge)
[![Spring Boot](https://img.shields.io/badge/spring-DDD?logo=spring&style=for-the-badge)](https://spring.io)
![Docker](https://img.shields.io/badge/docker-DDD?logo=docker&style=for-the-badge)
![Gradle](https://img.shields.io/badge/gradle-DDD?logo=gradle&style=for-the-badge)
![RabbitMQ](https://img.shields.io/badge/rabbitmq-DDD?logo=rabbitmq&style=for-the-badge)
![Postgresql](https://img.shields.io/badge/postgresql-DDD?logo=postgresql&style=for-the-badge)
![MockServer](https://img.shields.io/badge/mockserver-DDD?logo=mockserver&style=for-the-badge)
![ACME](https://github.com/tiagogn/acme/actions/workflows/gradle.yml/badge.svg)

### Documentação
Microserviços desenvolvido para o desafio de Engenheiro de Software da ACME.

Technologies utilizadas:
* java
* spring-boot
* docker-compose
* postgres
* rabbitmq
* mockserver

### Pré-requisitos
Antes de rodar o projeto, é necessário ter instalado:

* Java 17
* Docker
* Docker Compose

### Rodando o projeto

Para executar os requisitos de infraestrutura, dentro da raiz do projeto execute o comando abaixo:

```
docker-compose -f compose.yaml up
```
Com isso você terá o banco de dados Postgres, RabbitMQ e o MockServer executando. Ainda dentro da pasta do projeto você pode abri-lo no IntelliJ IDEA e rodar a aplicação.  

```
idea .
```

ou através do gradle:

```
./gradlew bootRun
``` 

### Endpoints

O projeto possui 3 endpoints:

* POST /api/v1/insurance-quote
* GET /api/v1/insurance-quote/:id
* POST /api/v1/insurance-policy

Link para o [Swagger](http://localhost:8080/acme/swagger-ui)

### POST /api/v1/insurance-quote

Endpoint utilizado para receber uma cotação de seguro. O payload esperado é:

```
{
    "product_id": "1b2da7cc-b367-4196-8a78-9cfeec21f587",
    "offer_id": "adc56d77-348c-4bf0-908f-22d402ee715c",
    "category": "HOME",
    "total_monthly_premium_amount": 75.25,
    "total_coverage_amount": 825000.00,
    "coverages":
    {
        "Incêndio": 250000.00,
        "Desastres naturais": 500000.00,
        "Responsabiliadade civil": 75000.00
    },
    "assistances":
    [
        "Encanador",
        "Eletricista",
        "Chaveiro 24h"
    ],
    "customer":
    {
        "document_number": "36205578902",
        "name": "John Wick",
        "type": "NATURAL",
        "gender": "MALE",
        "date_of_birth": "1973-05-02",
        "email": "johnwick@gmail.com",
        "phone_number": 11950503030
    }
}
```

### GET /api/v1/insurance-quote/:id

Endpoint utilizado para consultar uma cotação de seguro. O id é o id retornado no endpoint POST /api/v1/insurance-quote.

### POST /api/v1/insurance-policy

Endpoint utilizado para atualizar uma cotação de seguro com o número de uma apólice de seguro. O payload esperado é:

```
{
    "insuranceQuoteId": $id,
    "insurancePolicyId": 12345
}
```

### Detalhes técnicos adicionais do projeto

* O projeto foi desenvolvido utilizando a arquitetura hexagonal.
* O projeto possui testes unitários e de integração.
* O projeto possui validação de payload.
* O projeto possui tratamento de exceções.
* O projeto possui logs.
* O projeto possui documentação de API utilizando Swagger.
* O projeto possui um arquivo de docker-compose para execução dos requisitos de infraestrutura.
* O MockServer foi utilizado para simular o serviço de catalogo de produtos e ofertas.(http://localhost:3000/api/v1/catalog/).

### Considerações finais do desenvolvedor

Durante o desenvolvimento assumi algumas premissas como descrito no documento de requisitos. Segue abaixo:
* Mock para simular os endpoints de catalogo de produtos e ofertas. Mas mantive apenas 1 produto e 1 oferta.
* Não criei um dockerfile pois como dito no documento, isso não seria avaliado. Mas poderia ter criado e utilizado o 
compose para execução de todos os serviços.
* Devido ao tempo, alguns testes não foram totalmente implementados e algumas validações como as de entrada(Requisições)
não tiveram tempo de serem implementadas também. Mas caso fosse implementar, utilizaria o Bean Validation(javax.validation).
* Utilizei o Lombok dentro do Core da aplicação para facilitar a criação de POJOs. No dia-a-dia também utilizo com o mesmo intuito.
Não vejo necessidade de isolar o core de uma biblioteca que facilita a criação de POJOs.
* Não visualizei tanta interação entre os dominios, portanto não tem tantos patterns nessa camada. Ficando próximo de um domain anemico.
* Sobre Observabilidade, não ficou claro se vocês queriam ver os logs implementados ou se eu deveria rodar um Grafana, Prometheus, etc.

### Obrigado pela oportunidade e espero que gostem do projeto.
Entre trabalho e estudo da Pós, tive que me dividir para entregar o projeto. Mas fiz o meu melhor.
Adoraria receber um feedback, independente do resultado.
Muito obrigado!
Tiago.