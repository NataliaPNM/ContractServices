package com.example.contractservice.service;

import com.example.contractservice.mapper.CreateNewContractMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;
import ru.esb.xmlns.ext.contractservice.CreateNewContractResponse;


@Service
public class ContractService {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private CreateNewContractMapper createNewContractMapper;

    public CreateNewContractResponse createNewContract(CreateNewContractRequest request) throws JsonProcessingException {
        objectMapper.registerModule(new JavaTimeModule());
        CreateNewContractResponse response = new CreateNewContractResponse();
        try {
            template.convertAndSend("contract.create",
                    objectMapper.writeValueAsString(createNewContractMapper.toCreateNewContract(request)));
        } catch (AmqpException amqpException) {
            response.setErrorMessage(amqpException.getMessage());
            response.setStatus("Error");
        }

        response.setStatus("RequestIsQueued");
        return response;
    }

}
