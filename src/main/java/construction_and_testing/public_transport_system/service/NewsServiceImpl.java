package construction_and_testing.public_transport_system.service;

import construction_and_testing.public_transport_system.domain.News;
import construction_and_testing.public_transport_system.repository.NewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private NewsRepository newsRepository;

    @Override
    public List<News> getAll() {
        return newsRepository.findAll();
    }

    @Override
    public News getById(Long id) {
        if(newsRepository.findById(id).isPresent()){
            News news = newsRepository.findById(id).get();
            return news;
        }
        return null;
    }

    @Override
    public boolean addNew(News news) {
        try {
            newsRepository.saveAndFlush(news);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean modify(News news) {
        if(newsRepository.findById(news.getId()).isPresent()){
            newsRepository.saveAndFlush(news);
            return true;
        }
        return false;
    }
}
