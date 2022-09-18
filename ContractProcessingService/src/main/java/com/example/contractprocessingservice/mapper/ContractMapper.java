package com.example.contractprocessingservice.mapper;

import com.example.contractprocessingservice.entity.Contract;
import dto.CreateNewContract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContractMapper {
        @Mapping(target = "clientApi", source = "createNewContract.clientApi")
        Contract toContractFromCreateNewContract(CreateNewContract createNewContract);
}
