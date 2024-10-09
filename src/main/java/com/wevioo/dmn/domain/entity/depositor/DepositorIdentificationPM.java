package com.wevioo.dmn.domain.entity.depositor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositorIdentificationPM {
    private LegalForm legalForm;
    private String companyName;
    private String rne;
    private String otherIdentifierType;
    private String otherIdentifierNum;
    private String legalRepresentativeFirstname;
    private String legalRepresentativeLastname;
    private String birthDay;
    private String placeBirth;
    private String nationality;
}