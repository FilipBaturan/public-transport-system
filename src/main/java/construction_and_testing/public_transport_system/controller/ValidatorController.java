package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.service.definition.ValidatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/validator")
public class ValidatorController {

    private static final Logger logger = LoggerFactory.getLogger(ValidatorController.class);

    @Autowired
    private ValidatorService validatorService;


}
