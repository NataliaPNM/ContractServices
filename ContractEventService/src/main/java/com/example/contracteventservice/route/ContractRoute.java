package com.example.contracteventservice.route;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.ERROR;

@Component
public class ContractRoute extends RouteBuilder {

  @Value("${mockServiceAddress}")
  private String mockServiceAddress;

  @Override
  public void configure() {
    from("rabbitmq:amq.direct?queue=contract.event&routingKey=event&autoDelete=false")
        .log(ERROR, "Got this message from Rabbit: ${body}")
        .unmarshal()
        .json()
        .setHeader(Exchange.HTTP_METHOD, constant("POST"))
        .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
        .to(mockServiceAddress)
        .process(
            exchange ->
                log.info(
                    "The response code is: {}",
                    exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE)));
  }
}
