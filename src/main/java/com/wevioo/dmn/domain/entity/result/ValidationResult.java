package com.wevioo.dmn.domain.entity.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ValidationResult {
    private String dpoId;
    // Use a Map to store field name as the key and a list of errors as the value
    private List<AttributeValidationResult> attributeResults;
}