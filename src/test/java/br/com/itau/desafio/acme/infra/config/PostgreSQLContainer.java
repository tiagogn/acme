package br.com.itau.desafio.acme.infra.config;

public class PostgreSQLContainer  extends org.testcontainers.containers.PostgreSQLContainer<PostgreSQLContainer> {

    private static final String IMAGE_VERSION = "postgres:16-alpine";

    private static PostgreSQLContainer container;

    private PostgreSQLContainer() {
        super(IMAGE_VERSION);
    }

    public static PostgreSQLContainer getInstance() {
        if (container == null) {
            container = new PostgreSQLContainer();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}