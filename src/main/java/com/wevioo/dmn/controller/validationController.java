package com.wevioo.dmn.controller;

import com.wevioo.dmn.domain.entity.depositor.Depositor;
import com.wevioo.dmn.service.DepositorValidationServiceV2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/validation")
public class validationController {



    @Autowired
    private DepositorValidationServiceV2 depositorValidationServiceV2;

    @PostMapping("/validate-depositors-v2")
    public List<Map<String, Object>> validateDepositorsV2(@RequestBody List<Depositor> depositors) throws FileNotFoundException {
        return depositorValidationServiceV2.validateDepositors(depositors);
    }

}