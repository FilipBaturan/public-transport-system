package construction_and_testing.public_transport_system.service.definition;

import construction_and_testing.public_transport_system.domain.News;

import java.util.List;

public interface NewsService {

    /**
     * Method for getting all news
     *
     * @return all existing news
     */
    List<News> getAll();

    /**
     * Method for getting news, found by its id
     *
     * @param id - id from database
     * @return news with given id
     */
    News getById(Long id);

    /**
     * Method for adding news
     *
     * @param news - news to add
     * @return success flag (true if added, false if not)
     */
    boolean addNew(News news);

    /**
     * Method for changing news information
     *
     * @param news - news with modified information
     * @return success flag (true if added, false if not)
     */
    boolean modify(News news);

    /**
     * Method for removing news
     *
     * @param id - id of news for deleting
     */
    void remove(Long id);

}
