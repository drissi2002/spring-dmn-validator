package com.wevioo.dmn.domain.entity.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AttributeValidationResult {
    private String attributeName;
    private String attributeValue;
    private String error;
}