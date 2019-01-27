package construction_and_testing.public_transport_system.service.unit;

import construction_and_testing.public_transport_system.constants.NewsConstants;
import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.repository.NewsRepository;
import construction_and_testing.public_transport_system.service.definition.NewsService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static construction_and_testing.public_transport_system.constants.NewsConstants.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NewsServiceImplUnitTest {

    @MockBean
    private NewsRepository newsRepository;

    @Autowired
    private NewsService newsService;

    @Before
    public void setUp() {
        List<News> all = new ArrayList<>();
        all.add(DB_NEWS_1);
        all.add(DB_NEWS_2);
        all.add(DB_NEWS_3);
        when(newsRepository.findAll()).thenReturn(all);
        when(newsRepository.findById(DB_ID)).thenReturn(Optional.of(DB_NEWS_1));
        when(newsRepository.findById(DB_ID_2)).thenReturn(Optional.of(DB_NEWS_2));
        when(newsRepository.findById(DB_INVALID_ID)).thenReturn(Optional.empty());
        when(newsRepository.findById(null)).thenReturn(Optional.empty());
        when(newsRepository.saveAndFlush(DB_NEW_NEWS)).thenReturn(DB_NEW_NEWS);
        when(newsRepository.saveAndFlush(DB_CHANGED_NEWS)).thenReturn(DB_CHANGED_NEWS);
        when(newsRepository.save(DB_DELETED_NEWS)).thenReturn(DB_DELETED_NEWS);
    }


    /**
     * Valid test of getting all news in sorted order
     */
    @Test
    public void getAll() {
        List<News> allNews = newsService.getAll();
        boolean sorted = NewsConstants.isSorted(allNews);
        assertThat(allNews).isNotNull();
        assertTrue(sorted);
        verify(newsRepository, times(1)).findAll();
    }

    /**
     * Valid test for getting news by existing ID
     */
    @Test
    public void getById() {
        News news = newsService.getById(DB_ID);
        assertThat(news).isNotNull();
        assertEquals(news.getId(), DB_ID);
        assertEquals(news.getTitle(), DB_TITLE);
        assertEquals(news.getContent(), DB_CONTENT);
        assertEquals(news.getDate(), DB_TIME_1);
        verify(newsRepository, times(2)).findById(DB_ID);
    }

    /**
     * Test when invalid ID is given
     */
    @Test
    public void getByInvalidId() {
        News news = newsService.getById(DB_INVALID_ID);
        assertThat(news).isNull();
        verify(newsRepository, times(1)).findById(DB_INVALID_ID);
    }

    /**
     * Test when given ID is null
     */
    @Test
    public void getByNullId() {
        News news = newsService.getById(null);
        assertThat(news).isNull();
        verify(newsRepository, times(1)).findById(null);
    }

    /**
     *
     */
    @Test
    public void addNew() {
        boolean saved = newsService.addNew(DB_NEW_NEWS);
        assertTrue(saved);
        verify(newsRepository, times(1)).saveAndFlush(DB_NEW_NEWS);
    }

    @Test
    public void modifyExistingNews() {
        boolean modified = newsService.modify(DB_CHANGED_NEWS);
        assertTrue(modified);
        verify(newsRepository, times(1)).findById(DB_ID_2);
        verify(newsRepository, times(1 )).saveAndFlush(DB_CHANGED_NEWS);
    }

    @Test
    public void modifyUnexistingNews() {
        boolean modified = newsService.modify(DB_CHANGED_INVALID_NEWS);
        assertFalse(modified);
        verify(newsRepository, times(1)).findById(DB_INVALID_ID);
    }

    @Test
    public void removeNews() {
        newsService.remove(DB_ID);
        verify(newsRepository, times(1)).findById(DB_ID);
        verify(newsRepository, times(1)).save(DB_DELETED_NEWS);
    }

    @Test(expected = EntityNotFoundException.class)
    public void removeInvalidNews() {
        newsService.remove(DB_INVALID_ID);
        verify(newsRepository, times(1)).findById(DB_INVALID_ID);
    }
}
