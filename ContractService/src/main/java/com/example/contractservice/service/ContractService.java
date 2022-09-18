package com.example.contractservice.service;

import com.example.contractservice.mapper.CreateNewContractMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;
import ru.esb.xmlns.ext.contractservice.CreateNewContractResponse;

@Service
@RequiredArgsConstructor
public class ContractService {

  private final ObjectMapper objectMapper;
  private final AmqpTemplate template;
  private final CreateNewContractMapper createNewContractMapper;

  public CreateNewContractResponse createNewContract(CreateNewContractRequest request)
      throws JsonProcessingException {

    CreateNewContractResponse response = new CreateNewContractResponse();
    try {

      template.convertAndSend(
          "contract.create",
          objectMapper.writeValueAsString(createNewContractMapper.toCreateNewContract(request)));
    } catch (AmqpException amqpException) {
      // add logs
      response.setErrorMessage("При отправке сообщения произошла ошибка. Повторите попытку позже");
      response.setStatus("Error");
      return response;
    }

    response.setStatus("RequestIsQueued");
    return response;
  }
}
