package com.wevioo.dmn.domain.entity.depositor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContactDetails {
    private String address;
    private String country;
    private String phoneNumber1;
    private String phoneNumber2;
    private String email;
}