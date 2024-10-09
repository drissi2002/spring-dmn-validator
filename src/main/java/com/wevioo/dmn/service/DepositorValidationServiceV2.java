package com.wevioo.dmn.service;

import com.wevioo.dmn.domain.entity.depositor.Depositor;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class DepositorValidationServiceV2 {

    @Autowired
    private DmnEngine dmnEngine;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    CachingService cachingService;

    public List<Map<String, Object>> validateDepositors(List<Depositor> depositors) {
        List<Map<String, Object>> validationResultList = new ArrayList<>();

        // Load the DMN decision file from the classpath
        Resource resource = resourceLoader.getResource("classpath:dmn/depositorValidationV2.dmn.xml");

        try (InputStream dmnInputStream = resource.getInputStream()) {
            // Parse decisions
            DmnDecision decision = dmnEngine.parseDecision("DepositorValidationCommon", dmnInputStream);
            // Re-open the InputStream for each decision to avoid closed stream issue
            try (InputStream dmnInputStreamForPP = resource.getInputStream()) {
                DmnDecision decisionPP = dmnEngine.parseDecision("DepositorValidationPP", dmnInputStreamForPP);
                // Re-open the InputStream for the PM decision
                try (InputStream dmnInputStreamForPM = resource.getInputStream()) {
                    DmnDecision decisionPM = dmnEngine.parseDecision("DepositorValidationPM", dmnInputStreamForPM);

                    for (Depositor depositor : depositors) {
                        Map<String, Object> inputVariables = new HashMap<>();
                        Map<String, Object> validationResult = new LinkedHashMap<>();
                        Map<String, List<String>> fieldValidationMessages = new LinkedHashMap<>();

                        // Validate Country
                        validateCountry(depositor, inputVariables, decision, fieldValidationMessages);

                        // Validate DepositorIdentificationPP (Personal)
                        if (depositor.getDepositorIdentificationPP() != null) {
                            validateDepositorIdentificationPP(depositor, inputVariables, decisionPP, fieldValidationMessages);
                        }

                        // Validate DepositorIdentificationPM (Legal Entity)
                        if (depositor.getDepositorIdentificationPM() != null) {
                            validateDepositorIdentificationPM(depositor, inputVariables, decisionPM, fieldValidationMessages);
                        }

                        // Prepare the validation result
                        validationResult.put("dpoId", depositor.getDpoId());
                        validationResult.put("validationMessages", fieldValidationMessages.isEmpty()
                                ? Collections.singletonList("No validation rules applied")
                                : fieldValidationMessages);

                        validationResultList.add(validationResult);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error processing DMN file: " + e.getMessage(), e);
        }
        return validationResultList;
    }

    private void validateCountry(Depositor depositor, Map<String, Object> inputVariables, DmnDecision decision,
                                 Map<String, List<String>> fieldValidationMessages) {
        List<String> validCountries = cachingService.getCountries();
        inputVariables.put("country", depositor.getContactDetails().getCountry());
        inputVariables.put("validCountries", validCountries); // Pass the list of valid countries

        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decision, inputVariables);
        if (result != null) {
            collectValidationMessages("Country", result, fieldValidationMessages);
        }
    }

    private void validateDepositorIdentificationPP(Depositor depositor, Map<String, Object> inputVariables,
                                                   DmnDecision decisionPP, Map<String, List<String>> fieldValidationMessages) {
        inputVariables.put("cinNum", depositor.getDepositorIdentificationPP().getCinNum());
        inputVariables.put("passportNum", depositor.getDepositorIdentificationPP().getPassportNum());
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decisionPP, inputVariables);
        if (result != null) {
            collectValidationMessages("CIN", result, fieldValidationMessages);
            collectValidationMessages("Passport", result, fieldValidationMessages);
        }
    }

    private void validateDepositorIdentificationPM(Depositor depositor, Map<String, Object> inputVariables,
                                                   DmnDecision decisionPM, Map<String, List<String>> fieldValidationMessages) {
        inputVariables.put("legalForm", depositor.getDepositorIdentificationPM().getLegalForm().getLabel());
        DmnDecisionTableResult result = dmnEngine.evaluateDecisionTable(decisionPM, inputVariables);
        if (result != null) {
            collectValidationMessages("Legal Form", result, fieldValidationMessages);
        }
    }

    private void collectValidationMessages(String fieldName, DmnDecisionTableResult result,
                                           Map<String, List<String>> fieldValidationMessages) {
        List<String> validationMessages = result.collectEntries("Validation Result").stream()
                .map(Object::toString)
                .filter(message -> message.contains(fieldName)) // Filter messages specific to the field
                .collect(Collectors.toList());
        if (!validationMessages.isEmpty()) {
            fieldValidationMessages.put(fieldName.toLowerCase(), validationMessages); // Store using lower case key
        }
    }
}
