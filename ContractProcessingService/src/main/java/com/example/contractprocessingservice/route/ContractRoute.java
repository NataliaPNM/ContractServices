package com.example.contractprocessingservice.route;

import com.example.contractprocessingservice.mapper.ContractMapper;
import com.example.contractprocessingservice.repository.ContractRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ContractStatus;
import dto.CreateNewContract;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.DefaultMessage;
import org.springframework.stereotype.Component;

import static org.apache.camel.LoggingLevel.INFO;

@Component
@RequiredArgsConstructor
public class ContractRoute extends RouteBuilder {

  private final ContractRepository contractRepository;
  private final ContractMapper contractMapper;
  private final ObjectMapper objectMapper;

  @Override
  public void configure() {
    from("rabbitmq:amq.direct?queue=contract.create&routingKey=contracts&autoDelete=false")
        .log(INFO, "Got this message from Rabbit: ${body}")
        .unmarshal()
        .json()
        .process(this::createNewContract)
        .log(INFO, "After processing: ${body}")
        .marshal()
        .json()
        .to("rabbitmq:amq.direct?queue=contract.event&routingKey=event&autoDelete=false");
  }

  private void createNewContract(Exchange exchange) throws JsonProcessingException {
    CreateNewContract createNewContract =
        objectMapper.readValue(
            exchange.getMessage().getBody(String.class), CreateNewContract.class);
    Message message = new DefaultMessage(exchange);
    message.setBody(objectMapper.writeValueAsString(saveContract(createNewContract)));
    System.out.println(message.getBody().toString());
    exchange.setMessage(message);
  }

  private ContractStatus saveContract(CreateNewContract createNewContract) {
    ContractStatus contractStatus = new ContractStatus();
    contractStatus.setStatus(ContractStatus.Status.ERROR);
    if (contractRepository.findById(createNewContract.getId()).isPresent()) {
      contractStatus.setErrorCode(1);
      return contractStatus;
    } else if (contractRepository
        .findByContractNumber(createNewContract.getContractNumber())
        .isPresent()) {
      contractStatus.setErrorCode(2);
      return contractStatus;
    }

    ;
    contractStatus.setDateCreate(
        contractRepository
            .save(contractMapper.toContractFromCreateNewContract(createNewContract))
            .getDateCreate());
    contractStatus.setStatus(ContractStatus.Status.CREATED);
    contractStatus.setId(createNewContract.getId());
    return contractStatus;
  }
}
