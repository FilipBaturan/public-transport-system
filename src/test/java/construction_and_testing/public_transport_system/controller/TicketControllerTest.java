package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.domain.DTO.TicketReportDTO;
import construction_and_testing.public_transport_system.domain.enums.VehicleType;
import construction_and_testing.public_transport_system.service.definition.TicketService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Map;

import static construction_and_testing.public_transport_system.constants.TicketConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TicketControllerTest {

    private final String URL = "/api/ticket";

    @Autowired
    TicketService ticketService;

    @Autowired
    private TestRestTemplate testRestTemplate;


//    @Transactional
//    @Test
//    public void testCreateValid() throws IOException {
//        String jsonTicket = TestUtil.json(DB_NEW_TICKET);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<String> entity = new HttpEntity<String>(jsonTicket,headers);
//
//        ResponseEntity<Ticket> result = testRestTemplate
//                .postForEntity(this.URL, DB_NEW_TICKET, Ticket.class);
//
//
//        Ticket body = result.getBody();
//
//        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.CREATED);
//        assertThat(body).isNotNull();
//        assertThat(body.getExpiryDate()).isEqualTo(DB_EXP_DATE);
//        assertThat(body.getPurchaseDate()).isEqualTo(DB_PUR_DATE);
//        assertThat(body.getReservation()).isEqualTo(DB_RESERVATION);
//        assertThat(body.isActive()).isEqualTo(true);
//    }

    @Test
    public void getTicketsForUserValid() {
        ResponseEntity<TicketReportDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/getTicketsForUser/" + DB_USER_ID, TicketReportDTO[].class);

        TicketReportDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        assertEquals(body[0].getId(), DB_ID);
        assertEquals(body[0].getPurchaseDate(), DB_PUR_DATE);
        assertEquals(body[0].getExpiryDate(), DB_EXP_DATE);
        assertEquals(body[0].isActive(), true);
        assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void getTicketsForUserInvalid() {
        ResponseEntity<TicketReportDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/getTicketsForUser/" + DB_ID_INVALID, TicketReportDTO[].class);

        TicketReportDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body).isEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test(expected = RestClientException.class)
    public void getTicketsForUserNaN() {
        ResponseEntity<TicketReportDTO[]> result = testRestTemplate
                .getForEntity(this.URL + "/getTicketsForUser/" + "sad", TicketReportDTO[].class);

        TicketReportDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body).isEmpty();


    }


    @Test
    public void updateTicketValid() {
        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateTicket/", HttpMethod.PUT,
                        new HttpEntity<TicketReportDTO>(DB_TICKET_DTO), Boolean.class);

        Boolean body = result.getBody();

        assertTrue(body);
        assertEquals(result.getStatusCode(), HttpStatus.OK);

    }

    @Test
    public void updateTicketInvalid() {
        ResponseEntity<Boolean> result = testRestTemplate
                .exchange(this.URL + "/updateTicket/", HttpMethod.PUT,
                        new HttpEntity<TicketReportDTO>(DB_INVALID_TICKET_DTO), Boolean.class);

        Boolean body = result.getBody();

        assertFalse(body);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    public void getReportValid() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ParameterizedTypeReference<Map<VehicleType, Integer>> responseType = new ParameterizedTypeReference<Map<VehicleType, Integer>>() {
        };
        ResponseEntity<Map<VehicleType, Integer>> result = testRestTemplate
                .exchange(this.URL + "/reprot/2015-01-01/2018-12-12", HttpMethod.GET, entity, responseType);

        Map<VehicleType, Integer> prices = result.getBody();
        assertThat(prices).isNotEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(300);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(0);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(0);
    }

    @Test
    public void getReportSwappedDates() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ParameterizedTypeReference<Map<VehicleType, Integer>> responseType = new ParameterizedTypeReference<Map<VehicleType, Integer>>() {
        };
        ResponseEntity<Map<VehicleType, Integer>> result = testRestTemplate
                .exchange(this.URL + "/reprot/2018-01-01/2015-12-12", HttpMethod.GET, entity, responseType);

        Map<VehicleType, Integer> prices = result.getBody();
        assertThat(prices).isNotEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertThat(prices.get(VehicleType.BUS)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.METRO)).isEqualTo(-1);
        assertThat(prices.get(VehicleType.TRAM)).isEqualTo(-1);
    }

    /**
     * Date1 is not provided in correct form
     */
    @Test
    public void getReportInvalid() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ParameterizedTypeReference<Map<VehicleType, Integer>> responseType = new ParameterizedTypeReference<Map<VehicleType, Integer>>() {
        };
        ResponseEntity<Map<VehicleType, Integer>> result = testRestTemplate
                .exchange(this.URL + "/reprot/2015-01-0asdsd1/2018-12-12", HttpMethod.GET, entity, responseType);

        Map<VehicleType, Integer> prices = result.getBody();
        assertThat(prices).isEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void getVisitsPerWeekValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ParameterizedTypeReference<Map<String, Integer>> responseType = new ParameterizedTypeReference<Map<String, Integer>>() {
        };
        ResponseEntity<Map<String, Integer>> result = testRestTemplate
                .exchange(this.URL + "/getVisitsPerWeek/2015-01-01/2018-12-12", HttpMethod.GET, entity, responseType);

        Map<String, Integer> prices = result.getBody();
        assertThat(prices).isNotEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-12-26T00:00,2016-12-19T00:00")).isEqualTo(2);

    }

    @Test
    public void getVisitsPerWeekInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);

        ParameterizedTypeReference<Map<String, Integer>> responseType = new ParameterizedTypeReference<Map<String, Integer>>() {
        };
        ResponseEntity<Map<String, Integer>> result = testRestTemplate
                .exchange(this.URL + "/getVisitsPerWeek/2015-01-01qwewewe/2018-12-12", HttpMethod.GET, entity, responseType);

        Map<String, Integer> prices = result.getBody();
        assertThat(prices).isEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
    }

    @Test
    public void getVisitsPerMonthValid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ParameterizedTypeReference<Map<String, Integer>> responseType = new ParameterizedTypeReference<Map<String, Integer>>() {
        };
        ResponseEntity<Map<String, Integer>> result = testRestTemplate
                .exchange(this.URL + "/getVisitsPerMonth/2015-01-01/2018-12-12", HttpMethod.GET, entity, responseType);

        Map<String, Integer> prices = result.getBody();
        assertThat(prices).isNotEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.OK);
        assertThat(prices.size()).isEqualTo(1);
        assertThat(prices.get("2016-DECEMBER")).isEqualTo(2);
    }

    @Test
    public void getVisitsPerMonthInvalid() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ParameterizedTypeReference<Map<String, Integer>> responseType = new ParameterizedTypeReference<Map<String, Integer>>() {
        };
        ResponseEntity<Map<String, Integer>> result = testRestTemplate
                .exchange(this.URL + "/getVisitsPerMonth/2015-01-01/2018-asdsd2-12", HttpMethod.GET, entity, responseType);

        Map<String, Integer> prices = result.getBody();
        assertThat(prices).isEmpty();
        assertEquals(result.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);

    }
}
