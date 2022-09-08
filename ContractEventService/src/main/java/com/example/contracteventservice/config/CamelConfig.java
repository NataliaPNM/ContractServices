package com.example.contracteventservice.config;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CamelConfig {
  @Value("${spring.rabbitmq.host}")
  private String rabbitHost;

  @Value("${spring.rabbitmq.port}")
  private int rabbitPort;

  @Bean
  public ConnectionFactory сonnectionFactory() {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setHost(rabbitHost);
    factory.setPort(rabbitPort);
    return factory;
  }
}
