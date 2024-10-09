package com.wevioo.dmn.domain.entity.depositor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public  class Account {
    private String accountNumber;
    private String commercialName;
    private String accountTitle;
    private String accountNature;
    private String holdersCount;
    private String accountBalance;
    private String compensableAggregateBalance;
    private String vucIdCoAccountHolders;
    private String currency;
    private String accountBusinessName;
    private String bankCard;
    private String bankCardNumber;
    private String bankCardValidity;
    private String accountStatus;
    private String group2AccountSituation;
    private String group2AccountReason;
}