package com.example.contractprocessingservice.repository;

import com.example.contractprocessingservice.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContractRepository extends JpaRepository<Contract, UUID> {

    Optional<Contract> findById(UUID uuid);
    Optional<Contract> findByContractNumber(String number);

}
