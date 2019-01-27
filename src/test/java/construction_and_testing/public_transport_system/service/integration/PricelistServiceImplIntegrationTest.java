package construction_and_testing.public_transport_system.service.integration;


import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import construction_and_testing.public_transport_system.util.PricelistTimeIntervalException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.PricelistConstants.*;
import static construction_and_testing.public_transport_system.constants.PricelistConstants.DB_INVALID_NEW_PRICELIST_INTEGR_5;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricelistServiceImplIntegrationTest {

    @Autowired
    private PricelistService pricelistService;

    /**
     * Test for getting all price lists
     */
    @Test
    public void findAllPricelists(){
        List<Pricelist> allPricelists = pricelistService.findAllPricelists();
        assertThat(allPricelists).hasSize(DB_COUNT);
    }

    /**
     * Getting pricelist by valid ID
     */
    @Test
    public void findPricelistById(){
        Pricelist pricelist = pricelistService.findPricelistById(DB_INTEGR_ID);
        assertThat(pricelist).isNotNull();
        assertThat(pricelist.getId()).isEqualTo(DB_INTEGR_ID);
        assertThat(pricelist.getStartDate()).isEqualTo(DB_START_DATE_VALID_INTEGR);
        assertThat(pricelist.getEndDate()).isEqualTo(DB_END_DATE_VALID_INTEGR);
    }

    /**
     * Test for getting pricelist with invalid ID
     */
    @Test
    public void findByInvalidId(){
        Pricelist pricelist = pricelistService.findPricelistById(DB_INVALID_ID);
        assertThat(pricelist).isNull();
    }

    /**
     * Test for getting pricelist by null ID
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findByNullId(){
        pricelistService.findPricelistById(null);
    }

    /**
     * Test for getting active pricelist
     */
    @Test
    public void findValidTest(){
        Pricelist valid = pricelistService.findValid();
        assertThat(valid).isNotNull();
        assertEquals(valid.getId(), DB_INTEGR_ID);
        assertEquals(valid.getStartDate(), DB_START_DATE_VALID_INTEGR);
        assertEquals(valid.getEndDate(), DB_END_DATE_VALID_INTEGR);
    }

    /**
     * Test for saving new pricelist with valid time interval
     */
    @Test
    public void savePricelist(){
        Pricelist p = pricelistService.savePricelist(DB_VALID_NEW_PRICELIST_INTEGR);
        assertThat(p).isNotNull();
        assertThat(p.getStartDate()).isEqualTo(DB_NEW_START_DATE_VALID_INTEGR);
        assertThat(p.getEndDate()).isEqualTo(DB_NEW_END_DATE_VALID_INTEGR);
    }

    /**
     * Case 1: start date is after existing pricelist start date, end date is before existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase1(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_INTEGR_1);
    }

    /**
     * Case 2: start date is before existing pricelist start date, end date is after existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase2(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_INTEGR_2);
    }

    /**
     * Case 3: start date is before existing pricelist end date, end date is after existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase3(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_INTEGR_3);
    }

    /**
     * Case 4: start date is before existing pricelist start date, end date is after existing pricelist start date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase4(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_INTEGR_4);
    }

    /**
     * Case 5: start date and end date are the same as existing pricelist start date and end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase5(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_INTEGR_5);
    }

    /**
     * Test for removing pricelist with valid ID
     */
    @Test
    public void remove(){
        pricelistService.remove(DB_INTEGR_DELETE_ID);
        Pricelist pricelist = pricelistService.findPricelistById(DB_INTEGR_DELETE_ID);
        assertThat(pricelist).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeInvalid(){
        pricelistService.remove(DB_INVALID_ID);

    }
}
