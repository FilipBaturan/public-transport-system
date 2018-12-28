package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.converter.NewsConverter;
import construction_and_testing.public_transport_system.domain.DTO.NewsDTO;
import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.service.definition.NewsService;
import construction_and_testing.public_transport_system.util.GeneralException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/api/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /**
     * GET /api/news
     * <p>
     * Getting all news
     *
     * @return all news
     */
    @GetMapping
    //@PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<NewsDTO>> getAll() {
        logger.info("Fetching all news...");
        List<NewsDTO> allNews = NewsConverter.fromEntityList(newsService.getAll(), e -> NewsConverter.fromEntity(e));
        return new ResponseEntity<>(allNews, HttpStatus.OK);
    }

    /**
     * GET /api/news/{id}
     * <p>
     * Getting news news by given id
     *
     * @param id - id of news
     * @return news with given id
     */
    @GetMapping("{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<News> getById(@PathVariable Long id) {
        logger.info("Trying to get news with id " + id + ".");
        News news = newsService.getById(id);
        if (news != null) {
            logger.info("Successfully fetched news with id " + id + ".");
            return new ResponseEntity<>(news, HttpStatus.OK);
        } else {
            logger.warn("Cannot find news with id " + id + "!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * POST /api/news
     * <p>
     * Adding new news
     *
     * @param news - new news for adding
     * @return added news
     */
    @PostMapping
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<News> create(@RequestBody NewsDTO news) {
        News entity = NewsConverter.toEntity(news);
        boolean succeeded = newsService.addNew(NewsConverter.toEntity(news));
        if (succeeded) {
            logger.info("News added.");
            return new ResponseEntity<>(entity, HttpStatus.OK);
        } else {
            logger.warn("Cannot save news, probably some unique information are already exist!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * PUT /api/news/{id}
     * <p>
     * Modifiyng existing news
     *
     * @param id   - id of news for modifiyng
     * @param news - news with new information
     * @return modified news
     */
    @PutMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<News> update(@PathVariable Long id, @RequestBody News news) {
        news.setId(id);
        boolean succeeded = newsService.modify(news);
        if (succeeded) {
            logger.info("News successfully modified.");
            return new ResponseEntity<>(news, HttpStatus.OK);
        } else {
            logger.warn("Cannot modify news, probably news with given id doesn't exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * DELETE /api/news/{id}
     * <p>
     * Deleting existing news
     *
     * @param news for removing
     * @return feedback message
     */
    @DeleteMapping("/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<String> delete(@RequestBody News news) {
        logger.info("Deleting news with id {} at time {}.", news.getId(), Calendar.getInstance().getTime());
        try {
            newsService.remove(news.getId());
            return new ResponseEntity<>("News successfully deleted!", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new GeneralException("Requested news does not exist!", HttpStatus.NOT_FOUND);
        }
    }

}
