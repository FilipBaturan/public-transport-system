package construction_and_testing.public_transport_system.suits;


import construction_and_testing.public_transport_system.controller.ScheduleControllerTest;
import construction_and_testing.public_transport_system.controller.TicketControllerTest;
import construction_and_testing.public_transport_system.controller.UserControllerTest;
import construction_and_testing.public_transport_system.service.implementation.ScheduleServiceImpl;
import construction_and_testing.public_transport_system.service.integration.ScheduleServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.TicketServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.integration.UserServiceImplIntegrationTest;
import construction_and_testing.public_transport_system.service.unit.ScheduleServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.TicketServiceImplUnitTest;
import construction_and_testing.public_transport_system.service.unit.UserServiceImplUnitTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@RunWith(Suite.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Suite.SuiteClasses({
        ScheduleServiceImplIntegrationTest.class,
        ScheduleServiceImplUnitTest.class,
        ScheduleControllerTest.class,
        UserControllerTest.class

})
public class FBasSuite {
}
