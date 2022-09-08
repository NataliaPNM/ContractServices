package com.example.contractservice.mapper;

import dto.CreateNewContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.esb.xmlns.ext.contractservice.CreateNewContractRequest;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface CreateNewContractMapper {

  @Mapping(target = "dateSend", expression = "java(setCreationDate())")
  @Mapping(target = "clientApi", expression = "java(setClientApi())")
  @Mapping(target = "id", expression = "java(convert(request.getId()))")
  @Mapping(target = "contractualParties", source = "request.contractualParties.contractualParty")
  CreateNewContract toCreateNewContract(CreateNewContractRequest request);

  default LocalDateTime setCreationDate() {
    return LocalDateTime.now();
  }

  default UUID convert(String id) {
    return UUID.fromString(id);
  }

  default CreateNewContract.ClientApi setClientApi() {
    return CreateNewContract.ClientApi.SOAP;
  }
}
