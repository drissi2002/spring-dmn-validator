package com.wevioo.dmn.domain.entity.depositor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Depositor {
    private String dpoId;
    private DepositorIdentificationPP depositorIdentificationPP;
    private DepositorIdentificationPM depositorIdentificationPM;
    private ContactDetails contactDetails;
    private AccountsIdentification accountsIdentification;

}