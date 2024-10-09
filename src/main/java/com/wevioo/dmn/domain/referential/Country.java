package com.wevioo.dmn.domain.referential;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Country {
    private String countryCode;
    private String countryName;
}
