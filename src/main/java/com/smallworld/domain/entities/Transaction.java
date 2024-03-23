package com.smallworld.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @JsonProperty("mtn")
    private Integer mtn;

    @Getter
    @JsonProperty("amount")
    private BigDecimal amount;

    @Getter
    @JsonProperty("senderFullName")
    private String senderFullName;

    @JsonProperty("senderAge")
    private Integer senderAge;

    @Getter
    @JsonProperty("beneficiaryFullName")
    private String beneficiaryFullName;

    @JsonProperty("beneficiaryAge")
    private Integer beneficiaryAge;

    @Getter
    @JsonProperty("issueId")
    private Integer issueId;

    @JsonProperty("issueSolved")
    private Boolean issueSolved;

    @Getter
    @JsonProperty("issueMessage")
    private String issueMessage;

    public BigDecimal sortingDescAmount() {
        return amount.multiply(BigDecimal.valueOf(-1));
    }
}
