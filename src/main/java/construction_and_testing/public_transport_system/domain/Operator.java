package construction_and_testing.public_transport_system.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

/**
 * Add later
 */
@Entity
@DiscriminatorValue("op")
public class Operator extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "operator")
    private Set<News> news;

    public Operator() {
    }

    public Operator(Long id, String name, String lastName, String username, String password, String email,
                    String telephone, boolean confirmation, Set<News> news) {
        super(id, name, lastName, username, password, email, telephone, confirmation);
        this.news = news;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<News> getNews() {
        return news;
    }

    public void setNews(Set<News> news) {
        this.news = news;
    }
}
