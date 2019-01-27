package construction_and_testing.public_transport_system.service.integration;


import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.domain.Operator;
import construction_and_testing.public_transport_system.service.definition.NewsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

import static construction_and_testing.public_transport_system.constants.NewsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsServiceImplIntegrationTest {

    @Autowired
    private NewsService newsService;

    @Test
    public void getAll() {
        List<News> all = newsService.getAll();
        assertThat(all).isNotNull();
        assertThat(all).hasSize(DB_COUNT);
    }

    /**
     * Valid test for getting news by existing ID
     */
    @Test
    public void getById() {
        News news = newsService.getById(DB_ID);
        assertThat(news.getId()).isEqualTo(DB_ID);
        assertThat(news.getDate()).isEqualTo(DB_TIME_1);
        assertThat(news.getTitle()).isEqualTo(DB_TITLE);
        assertThat(news.getContent()).isEqualTo(DB_CONTENT);
    }

    /**
     * Test when invalid ID is given
     */
    @Test
    public void getByInvalidId() {
        News news = newsService.getById(DB_INVALID_ID);
        assertThat(news).isNull();
    }

    /**
     * Test when given ID is null
     */
    @Test(expected = IllegalArgumentException.class)
    public void getByNullId() {
        newsService.getById(null);
    }

    /**
     * Valid test of adding new news
     */
    @Test
    public void addNew() {
        News news = new News(null, "Title4", "Content4",
                LocalDateTime.of(2019, 1, 20, 10, 10), null);
        Operator o = new Operator();
        o.setId(3L);
        news.setOperator(o);
        boolean saved = newsService.addNew(news);
        assertThat(saved).isTrue();
    }

    /**
     * Test when title of news is missing
     */
    @Test
    public void addNewWithNullTitle() {
        News news = new News(null, null, "Content4",
                LocalDateTime.of(2019, 1, 20, 10, 10), null);
        Operator o = new Operator();
        o.setId(3L);
        news.setOperator(o);
        boolean saved = newsService.addNew(news);
        assertThat(saved).isFalse();
    }

    /**
     * Test when content of news is missing
     */
    @Test
    public void addNewWithContentNull() {
        News news = new News(null, "Title4", null,
                LocalDateTime.of(2019, 1, 20, 10, 10), null);
        Operator o = new Operator();
        o.setId(3L);
        news.setOperator(o);
        boolean saved = newsService.addNew(news);
        assertThat(saved).isFalse();
    }

    /**
     * Valid test for modifying news
     */
    @Test
    public void modify() {
        boolean modified = newsService.modify(DB_CHANGED_NEWS);
        assertThat(modified).isTrue();
        News news = newsService.getById(DB_ID);
        assertThat(news.getTitle()).isEqualTo(DB_CHANGED_TITLE);
        assertThat(news.getContent()).isEqualTo(DB_CHANGED_CONTENT);
    }

    /**
     * Test when given news contains invalid ID
     */
    @Test
    public void modifyWithInvalidId() {
        boolean saved = newsService.modify(DB_CHANGED_INVALID_NEWS);
        assertThat(saved).isFalse();
    }

    /**
     * Valid test of deleting news with given ID
     */
    @Test
    public void delete() {
        newsService.remove(DB_ID);
        News news = newsService.getById(DB_ID);
        assertThat(news).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteInvalid() {
        newsService.remove(DB_INVALID_ID);
    }

}
