package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.service.TransportLineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transportLine")
public class TransportLineController {

    private static final Logger logger = LoggerFactory.getLogger(TransportLineController.class);

    @Autowired
    private TransportLineService transportLineService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getAll() {
        //add implementation later
    }


}