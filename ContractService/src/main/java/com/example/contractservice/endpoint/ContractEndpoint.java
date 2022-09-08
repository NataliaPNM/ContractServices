package com.example.contractservice.endpoint;

import com.example.contractservice.service.ContractService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;
import ru.esb.xmlns.ext.contractservice.CreateNewContractResponse;

@Endpoint
public class ContractEndpoint {
  private static final String NAMESPACE_URI = "http://xmlns.esb.ru/ext/ContractService/";

  private ContractService contractService;

  @Autowired
  public ContractEndpoint(ContractService contractService) {
    this.contractService = contractService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateNewContractRequest")
  @ResponsePayload
  public CreateNewContractResponse createNewContract(
      @RequestPayload CreateNewContractRequest request) throws JsonProcessingException {

    return contractService.createNewContract(request);
  }
}
