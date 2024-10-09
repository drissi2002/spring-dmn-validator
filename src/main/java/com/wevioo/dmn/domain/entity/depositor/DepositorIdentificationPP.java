package com.wevioo.dmn.domain.entity.depositor;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepositorIdentificationPP {
    private String personType;
    private String cinNum;
    private String cinIssueDate;
    private String passportNum;
    private String passportValidityDate;
    private String residenceCertificate;
    private String residenceCertificateDateResidency;
    private String birthDay;
    private String placeBirth;
    private String nationality;
    private String tutor;
}