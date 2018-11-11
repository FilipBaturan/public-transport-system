package construction_and_testing.public_transport_system.domain.DTO;

import construction_and_testing.public_transport_system.domain.News;

import java.io.Serializable;
import java.time.LocalDateTime;

public class NewsDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String title;

    private String content;

    private LocalDateTime date;

    private boolean active;

    public NewsDTO() {
        active = true;
    }

    public NewsDTO(Long id, String title, String content, LocalDateTime date) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
        this.active = true;
    }

    public NewsDTO(News news){
        this.id = news.getId();
        this.title = news.getTitle();
        this.content = news.getContent();
        this.date = news.getDate();
        this.active = news.isActive();
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
