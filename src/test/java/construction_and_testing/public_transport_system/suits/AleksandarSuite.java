package construction_and_testing.public_transport_system.suits;

import construction_and_testing.public_transport_system.controller.NewsControllerTest;
import construction_and_testing.public_transport_system.controller.PricelistControllerTest;
import construction_and_testing.public_transport_system.controller.ReservationControllerTest;
import construction_and_testing.public_transport_system.service.integration.NewsServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.PricelistServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.RegisteredUserServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.ReservationServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.unit.NewsServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.PricelistServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.RegisteredUserServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.ReservationServiceImplUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suite.SuiteClasses({
        RegisteredUserServiceImplUnitTest.class,
        RegisteredUserServiceImplIntegrationTest.class,
        PricelistServiceImplUnitTest.class,
        PricelistServiceImplIntegrationTest.class,
        NewsServiceImplUnitTest.class,
        NewsServiceImplIntegrationTest.class,
        ReservationServiceImplUnitTest.class,
        ReservationServiceImplIntegrationTest.class,
        NewsControllerTest.class,
        PricelistControllerTest.class,
})
public class AleksandarSuite {
}
