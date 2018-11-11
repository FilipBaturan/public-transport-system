package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/pricelist")
public class PricelistController {

    private static final Logger logger = LoggerFactory.getLogger(PricelistController.class);

    @Autowired
    private PricelistService pricelistService;

    @RequestMapping(method = RequestMethod.GET, value = "/savePricelist", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pricelist> savePricelist(@RequestBody Pricelist pricelist) {
        logger.info("Adding pricelist at time {}.", Calendar.getInstance().getTime());
        Pricelist p = this.pricelistService.savePricelist(pricelist);
        if( p != null){
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        }else{
            throw new GeneralException("Pricelist with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findAllPricelists", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Pricelist>> findAllPricelists() {
        logger.info("Requesting all available pricelists at time {}.", Calendar.getInstance().getTime());
        List<Pricelist> pricelists = this.pricelistService.findAllPricelists();
        return new ResponseEntity<>(pricelists, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/findPricelistById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Pricelist> findPricelistById(@PathVariable("id") String id) {
        logger.info("Requesting pricelist with id {} at time {}.", id, Calendar.getInstance().getTime());
        try {
            Pricelist pricelist = this.pricelistService.findPricelistById(Long.parseLong(id));
            return new ResponseEntity<>(pricelist, HttpStatus.FOUND);
        } catch (NumberFormatException e) {
            throw new GeneralException("Bad format of requested id!", HttpStatus.BAD_REQUEST);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested pricelist does not exist!", HttpStatus.NOT_FOUND);
        }
    }

}
