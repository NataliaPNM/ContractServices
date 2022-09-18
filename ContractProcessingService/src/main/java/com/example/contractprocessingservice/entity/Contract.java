package com.example.contractprocessingservice.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import dto.ContractualParty;
import dto.CreateNewContract;
import lombok.*;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Entity
@Table(name = "contract")
@NoArgsConstructor
@AllArgsConstructor
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class Contract {
  @Id @EqualsAndHashCode.Exclude private UUID id;

  private String dateStart;

  private String dateEnd;

  private LocalDateTime dateSend;

  @UpdateTimestamp
  private LocalDateTime dateCreate;

  private String contractNumber;

  private String contractName;

  private String clientApi;

  @Type(type = "jsonb")
  private List<ContractualParty> contractualParties = new ArrayList<>();
}
