package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.domain.util.GeneralException;
import construction_and_testing.public_transport_system.service.definition.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Calendar;
import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    /**
     * Getting all news
     * @return all news
     */
    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<News>> getAll(){
        logger.info("Fetching all news...");
        List<News> allNews = newsService.getAll();
        return new ResponseEntity<>(allNews, HttpStatus.OK);
    }

    /**
     * Getting news news by given id
     * @param id - id of news
     * @return news with given id
     */
    @RequestMapping(method = RequestMethod.GET, value = "/getById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> getById(@PathVariable Long id){
        logger.info("Trying to get news with id " + id + ".");
        News news = newsService.getById(id);
        if(news != null){
            logger.info("Successfully fetched news with id " + id + ".");
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        else {
            logger.warn("Cannot find news with id " + id + "!");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Adding new news
     * @param news - new news for adding
     * @return added news
     */
    @RequestMapping(method = RequestMethod.POST, value = "/addNew", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> addNewUser(@RequestBody News news){
        boolean succeeded = newsService.addNew(news);
        if(succeeded){
            logger.info("News added.");
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        else{
            logger.warn("Cannot save news, probably some unique information are already exist!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Modifiyng existing news
     * @param id - id of news for modifiyng
     * @param news - news with new information
     * @return modified news
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/modify/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<News> modify(@PathVariable Long id, @RequestBody News news){
        news.setId(id);
        boolean succeeded = newsService.modify(news);
        if(succeeded){
            logger.info("News successfully modified.");
            return new ResponseEntity<>(news, HttpStatus.OK);
        }
        else{
            logger.warn("Cannot modify news, probably news with given id doesn't exists!");
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    /**
     * Deleting existing news
     * @param news for removing
     * @return feedback message
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/delete", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteNews(@RequestBody News news) {
        logger.info("Deleting news with id {} at time {}.", news.getId(), Calendar.getInstance().getTime());
        try {
            newsService.remove(news.getId());
            return new ResponseEntity<>("News successfully deleted!", HttpStatus.OK);
        }catch (EntityNotFoundException e){
            throw new GeneralException("Requested news does not exist!", HttpStatus.NOT_FOUND);
        }
    }

}
