package construction_and_testing.public_transport_system.suits;

import construction_and_testing.public_transport_system.service.integration.PricelistServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.RegisteredUserServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.unit.PricelistServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.RegisteredUserServiceImplUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suite.SuiteClasses({
        RegisteredUserServiceImplUnitTest.class,
        RegisteredUserServiceImplIntegrationTest.class,
        PricelistServiceImplUnitTest.class,
        PricelistServiceImplIntegrationTest.class
})
public class AleksandarSuite {
}
