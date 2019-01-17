package construction_and_testing.public_transport_system.suits;

import construction_and_testing.public_transport_system.controller.StationControllerTest;
import construction_and_testing.public_transport_system.controller.TransportLineControllerTest;
import construction_and_testing.public_transport_system.controller.VehicleControllerTest;
import construction_and_testing.public_transport_system.controller.ZoneControllerTest;
import construction_and_testing.public_transport_system.service.integration.StationServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.TransportLineServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.VehicleServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.ZoneServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.unit.StationServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.TransportLineServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.VehicleServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.ZoneServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.upload.ImageServiceTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Suite.SuiteClasses({
        StationServiceImplIntegrationTest.class,
        StationServiceImplUnitTest.class,
        StationControllerTest.class,
        TransportLineServiceImplIntegrationTest.class,
        TransportLineServiceImplUnitTest.class,
        TransportLineControllerTest.class,
        VehicleServiceImplIntegrationTest.class,
        VehicleServiceImplUnitTest.class,
        VehicleControllerTest.class,
        ZoneServiceImplIntegrationTest.class,
        ZoneServiceImplUnitTest.class,
        ZoneControllerTest.class,
        ImageServiceTest.class
})
public class DanijelSuite {
}
