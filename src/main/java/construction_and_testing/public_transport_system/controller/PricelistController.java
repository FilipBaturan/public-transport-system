package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.service.PricelistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pricelist")
public class PricelistController {

    private static final Logger logger = LoggerFactory.getLogger(PricelistController.class);

    @Autowired
    private PricelistService pricelistService;

    @RequestMapping(method = RequestMethod.GET, value = "/savePricelist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pricelist> findPricelistById(@RequestBody Pricelist pricelist) {
        Pricelist p = this.pricelistService.savePricelist(pricelist);
        return new ResponseEntity<>(p, HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllPricelists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pricelist>> findAllPricelists() {
        List<Pricelist> pricelists = this.pricelistService.findAllPricelists();
        return new ResponseEntity<>(pricelists, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPricelistById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pricelist> findPricelistById(@PathVariable("id") String id) {
        Pricelist pricelist = this.pricelistService.findPricelistById(Long.parseLong(id));
        return new ResponseEntity<>(pricelist, HttpStatus.OK);
    }

}
