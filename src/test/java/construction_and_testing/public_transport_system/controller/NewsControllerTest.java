package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.converter.NewsConverter;
import construction_and_testing.public_transport_system.domain.DTO.AddNewsDTO;
import construction_and_testing.public_transport_system.domain.DTO.NewsDTO;
import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.service.definition.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import static construction_and_testing.public_transport_system.constants.NewsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class NewsControllerTest {

    private final String URL = "/api/news";

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private NewsService newsService;

    @Test
    public void getAll(){
        ResponseEntity<NewsDTO[]> result = testRestTemplate.getForEntity(this.URL, NewsDTO[].class);
        NewsDTO[] body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body).hasSize(DB_COUNT);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void create(){
        AddNewsDTO newNews = new AddNewsDTO("New title", "New content", 3L);
        ResponseEntity<News> result = testRestTemplate.exchange(this.URL, HttpMethod.POST, new HttpEntity<AddNewsDTO>(newNews), News.class);
        News created = result.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getTitle()).isEqualTo("New title");
        assertThat(created.getContent()).isEqualTo("New content");
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void getByIdUnauthorized(){
        ResponseEntity<News> result = testRestTemplate.getForEntity(this.URL + "/" + DB_ID, News.class);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    public void update(){
        NewsDTO dto = NewsConverter.fromEntity(DB_CHANGED_NEWS_2);
        ResponseEntity<News> result = testRestTemplate.
                exchange(this.URL + "/" + dto.getId(), HttpMethod.PUT, new HttpEntity<NewsDTO>(dto), News.class);
        News updated = result.getBody();
        assertThat(updated).isNotNull();
        assertThat(updated.getId()).isEqualTo(DB_CHANGED_NEWS_2.getId());
        assertThat(updated.getTitle()).isEqualTo(DB_CHANGED_NEWS_2.getTitle());
        assertThat(updated.getContent()).isEqualTo(DB_CHANGED_NEWS_2.getContent());
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void updateInvalid(){
        NewsDTO dto = NewsConverter.fromEntity(DB_CHANGED_NEWS_2);
        ResponseEntity<News> result = testRestTemplate.
                exchange(this.URL + "/" + 124235L, HttpMethod.PUT, new HttpEntity<NewsDTO>(dto), News.class);
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.CONFLICT);
    }

    @Test
    public void delete(){
        Long DB_DELETE_ID = 4L;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> result = testRestTemplate.
                exchange(this.URL + "/" + DB_DELETE_ID, HttpMethod.DELETE, entity, String.class);
        String body = result.getBody();
        assertThat(body).isEqualTo("News successfully deleted!");
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.OK);
    }

    @Test
    public void deleteInvalid(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("", headers);
        ResponseEntity<String> result = testRestTemplate.
                exchange(this.URL + "/" + DB_INVALID_ID, HttpMethod.DELETE, entity, String.class);
        String body = result.getBody();
        assertThat(body).isEqualTo("Requested news does not exist!");
        assertThat(result.getStatusCode()).isEqualByComparingTo(HttpStatus.NOT_FOUND);
    }

}
