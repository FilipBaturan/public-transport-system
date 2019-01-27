package construction_and_testing.public_transport_system.service.integration;

import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.service.definition.RegisteredUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RegisteredUserServiceImplIntegrationTest {

    @Autowired
    private RegisteredUserService registeredUserService;

    @Test
    public void getAll(){
        List<RegisteredUser> all = registeredUserService.getAll();
        assertThat(all).hasSize(1);
    }

    @Test
    public void getById(){

    }

    @Test
    public void getByInvalidId(){

    }

    @Test
    public void getByNullId(){

    }

    @Test
    public void addNew(){

    }

    @Test
    public void modify(){

    }

    @Test
    public void modifyWithInvalidId(){

    }

    @Test
    public void remove(){

    }

    @Test
    public void removeWithInvalidId(){

    }

    @Test
    public void removeWithNullId(){

    }

}
