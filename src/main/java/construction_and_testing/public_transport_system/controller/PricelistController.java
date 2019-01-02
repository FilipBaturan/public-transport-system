package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.converter.PriceListConverter;
import construction_and_testing.public_transport_system.domain.DTO.PricelistDTO;
import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/pricelist")
public class PricelistController {

    private static final Logger logger = LoggerFactory.getLogger(PricelistController.class);

    @Autowired
    private PricelistService pricelistService;

    /**
     * POST /api/pricelist
     * <p>
     * Controller method for adding new pricelists
     *
     * @param pricelist - pricelist that we want to add
     * @return added pricelist
     */
    @PostMapping
    public ResponseEntity<Pricelist> create(@RequestBody Pricelist pricelist) {
        logger.info("Adding pricelist at time {}.", Calendar.getInstance().getTime());
        Pricelist p = this.pricelistService.savePricelist(pricelist);
        if (p != null) {
            return new ResponseEntity<>(p, HttpStatus.CREATED);
        } else {
            throw new GeneralException("Pricelist with given name already exist!", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("findActive")
    public ResponseEntity<PricelistDTO> findActive() {
        Pricelist p = pricelistService.findValid();
        return new ResponseEntity<>(PriceListConverter.fromEntity(p), HttpStatus.OK);
    }

    /**
     * GET /api/pricelist
     * <p>
     * Getting all pricelists
     *
     * @return all pricelists
     */
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<Pricelist>> findAll() {
        logger.info("Requesting all available pricelists at time {}.", Calendar.getInstance().getTime());
        List<Pricelist> pricelists = this.pricelistService.findAllPricelists();
        return new ResponseEntity<>(pricelists, HttpStatus.OK);
    }

    /**
     * GET /api/pricelist/{id}
     * <p>
     * Getting pricelist by given id
     *
     * @param id - id of pricelist
     * @return pricelist with given id
     */
    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Pricelist> findById(@PathVariable("id") String id) {
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
