package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.PriceListConverter;
import construction_and_testing.public_transport_system.domain.DTO.PricelistDTO;
import construction_and_testing.public_transport_system.domain.Pricelist;
import construction_and_testing.public_transport_system.service.definition.PricelistService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static construction_and_testing.public_transport_system.constants.PricelistConstants.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PricelistControllerTest {

    private final String URL = "/api/pricelist";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private PricelistService pricelistService;

    @Test
    public void findActive(){
        ResponseEntity<PricelistDTO> result = testRestTemplate
                .getForEntity(this.URL + "/findActive", PricelistDTO.class);
        PricelistDTO body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getId()).isEqualTo(DB_INTEGR_ID);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void create(){
        PricelistDTO dto = PriceListConverter.fromEntity(DB_VALID_NEW_PRICELIST_INTEGR);
        ResponseEntity<Object> result = testRestTemplate.
                exchange(this.URL, HttpMethod.POST, new HttpEntity<PricelistDTO>(dto), Object.class);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
    }

    @Test
    public void modify(){
        ResponseEntity<Object> result = testRestTemplate.
                exchange(this.URL + "/modify", HttpMethod.PUT, new HttpEntity<Pricelist>(DB_MODIFIED_PRICELIST_INTEGR), Object.class);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CREATED);
    }

}
