package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.repository.ReservationRepository;
import construction_and_testing.public_transport_system.service.definition.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationServiceImplUnitTest {

    @MockBean
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationService reservationService;

    @Before
    public void setUp(){

    }

    /**
     * Test for reserving tickets
     */
    @Test
    public void reserve(){

    }



}
