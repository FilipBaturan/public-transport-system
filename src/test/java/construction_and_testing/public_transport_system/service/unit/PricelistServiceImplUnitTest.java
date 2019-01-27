package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.repository.PricelistRepository;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import construction_and_testing.public_transport_system.util.PricelistTimeIntervalException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.PricelistConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricelistServiceImplUnitTest {

    @MockBean
    private PricelistRepository pricelistRepository;

    @Autowired
    private PricelistService pricelistService;

    @Before
    public void setUp() {
        List<Pricelist> pricelists = new ArrayList<>();
        DB_VALID_PRICELIST.setStartDate(DB_START_DATE_VALID);
        DB_VALID_PRICELIST.setEndDate(DB_END_DATE_VALID);
        pricelists.add(DB_VALID_PRICELIST);
        pricelists.add(DB_EXPIRED_PRICELIST);
        when(pricelistRepository.findAll()).thenReturn(pricelists);
        when(pricelistRepository.findById(DB_ID)).thenReturn(Optional.of(DB_VALID_PRICELIST));
        when(pricelistRepository.findById(DB_EXPIRED_ID)).thenReturn(Optional.of(DB_EXPIRED_PRICELIST));
        when(pricelistRepository.findById(DB_INVALID_ID)).thenReturn(Optional.empty());
        when(pricelistRepository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
        when(pricelistRepository.save(DB_VALID_NEW_PRICELIST)).thenReturn(DB_VALID_NEW_PRICELIST);
        when(pricelistRepository.save(DB_INVALID_NEW_PRICELIST_1)).thenThrow(PricelistTimeIntervalException.class);
        when(pricelistRepository.save(DB_INVALID_NEW_PRICELIST_2)).thenThrow(PricelistTimeIntervalException.class);
        when(pricelistRepository.save(DB_INVALID_NEW_PRICELIST_3)).thenThrow(PricelistTimeIntervalException.class);
        when(pricelistRepository.save(DB_INVALID_NEW_PRICELIST_4)).thenThrow(PricelistTimeIntervalException.class);
        when(pricelistRepository.save(DB_INVALID_NEW_PRICELIST_5)).thenThrow(PricelistTimeIntervalException.class);
        when(pricelistRepository.save(DB_VALID_PRICELIST)).thenReturn(DB_MODIFIED_PRICELIST);
    }

    /**
     * Test for getting active pricelist
     */
    @Test
    public void findValidTest() {
        Pricelist valid = pricelistService.findValid();
        assertThat(valid).isNotNull();
        assertEquals(valid.getId(), DB_ID);
        assertEquals(valid.getStartDate(), DB_START_DATE_VALID);
        assertEquals(valid.getEndDate(), DB_END_DATE_VALID);
        assertTrue(valid.isActive());
        verify(pricelistRepository, times(1)).findAll();
    }

    /**
     * Getting pricelist by valid ID
     */
    @Test
    public void findPricelistById(){
        Pricelist pricelist = pricelistService.findPricelistById(DB_ID);
        assertThat(pricelist).isNotNull();
        assertThat(pricelist.getId()).isEqualTo(DB_ID);
        assertThat(pricelist.getStartDate()).isEqualTo(DB_START_DATE_VALID);
        verify(pricelistRepository, times(2)).findById(DB_ID);
    }

    /**
     * Test for getting pricelist with invalid ID
     */
    @Test
    public void findByInvalidId(){
        Pricelist pricelist = pricelistService.findPricelistById(DB_INVALID_ID);
        assertThat(pricelist).isNull();
        verify(pricelistRepository, times(1)).findById(DB_INVALID_ID);
    }

    /**
     * Test for getting pricelist by null ID
     */
    @Test(expected = InvalidDataAccessApiUsageException.class)
    public void findByNullId(){
        pricelistService.findPricelistById(null);
    }

    /**
     * Test for saving new pricelist with valid time interval
     */
    @Test
    public void savePricelist(){
        Pricelist p = pricelistService.savePricelist(DB_VALID_NEW_PRICELIST);
        assertThat(p).isNotNull();
        assertThat(p.getId()).isEqualTo(DB_NEW_ID);
        assertThat(p.getStartDate()).isEqualTo(DB_NEW_START_DATE_VALID);
        assertThat(p.getEndDate()).isEqualTo(DB_NEW_END_DATE_VALID);
        verify(pricelistRepository, times(1)).save(DB_VALID_NEW_PRICELIST);
    }

    /**
     * Case 1: start date is after existing pricelist start date, end date is before existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase1(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_1);
    }

    /**
     * Case 2: start date is before existing pricelist start date, end date is after existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase2(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_2);
    }

    /**
     * Case 3: start date is before existing pricelist end date, end date is after existing pricelist end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase3(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_3);
    }

    /**
     * Case 4: start date is before existing pricelist start date, end date is after existing pricelist start date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase4(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_4);
    }

    /**
     * Case 5: start date and end date are the same as existing pricelist start date and end date
     */
    @Test(expected = PricelistTimeIntervalException.class)
    public void savePricelistInvalidCase5(){
        pricelistService.savePricelist(DB_INVALID_NEW_PRICELIST_5);
    }

    /**
     * Test for modifying existing pricelist
     */
    @Test
    public void modify(){
        Pricelist modified = pricelistService.modify(DB_VALID_PRICELIST);
        verify(pricelistRepository, times(1)).saveAndFlush(DB_VALID_PRICELIST);
    }

    /**
     * Test for removing pricelist with valid ID
     */
    @Test
    public void remove(){
        pricelistService.remove(DB_EXPIRED_ID);
        verify(pricelistRepository, times(1)).findById(DB_EXPIRED_ID);
        verify(pricelistRepository, times(1)).save(DB_EXPIRED_PRICELIST);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeInvalid(){
        pricelistService.remove(DB_INVALID_ID);
    }
}
