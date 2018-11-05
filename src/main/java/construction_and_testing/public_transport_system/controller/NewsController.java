package construction_and_testing.public_transport_system.controller;


import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.domain.RegisteredUser;
import construction_and_testing.public_transport_system.service.NewsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/news")
public class NewsController {

    private static final Logger logger = LoggerFactory.getLogger(NewsController.class);

    @Autowired
    private NewsService newsService;

    @RequestMapping(method = RequestMethod.GET, value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<News>> getAll(){
        logger.info("Fetching all news...");
        List<News> allNews = newsService.getAll();
        return new ResponseEntity<>(allNews, HttpStatus.OK);
    }

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



}
