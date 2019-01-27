package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.repository.PricelistRepository;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.PricelistConstants.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PricelistServiceImplUnitTest {

    @MockBean
    private PricelistRepository pricelistRepository;

    @Autowired
    private PricelistService pricelistService;

    @Before
    public void setUp(){
        List<Pricelist> pricelists = new ArrayList<>();
        pricelists.add(DB_VALID_PRICELIST);
        pricelists.add(DB_EXPIRED_PRICELIST);
        when(pricelistRepository.findAll()).thenReturn(pricelists);
    }

    @Test
    public void findValidTest(){
        Pricelist valid = pricelistService.findValid();
        assertThat(valid).isNotNull();
        assertEquals(valid.getId(), DB_ID);
        assertEquals(valid.getStartDate(), DB_START_DATE_VALID);
        assertEquals(valid.getEndDate(), DB_END_DATE_VALID);
        assertTrue(valid.isActive());
        verify(pricelistRepository, times(1)).findAll();
    }
}
