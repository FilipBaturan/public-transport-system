package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.service.definition.RegisteredUserService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisteredUserServiceImplIntegrationTest {

    @Autowired
    private RegisteredUserService registeredUserService;



}
