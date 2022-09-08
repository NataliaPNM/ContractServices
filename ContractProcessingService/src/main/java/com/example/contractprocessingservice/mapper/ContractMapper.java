package com.example.contractprocessingservice.mapper;

import com.example.contractprocessingservice.entity.Contract;
import dto.CreateNewContract;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ContractMapper {

        Contract toContractFromCreateNewContract(CreateNewContract createNewContract);
}
