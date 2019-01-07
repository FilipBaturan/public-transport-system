package construction_and_testing.public_transport_system.domain.DTO;

public class AddNewsDTO {

    private String title;

    private String content;

    private Long operator;

    public AddNewsDTO() {
    }

    public AddNewsDTO(String title, String content, Long operator) {
        this.title = title;
        this.content = content;
        this.operator = operator;
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

    public Long getOperator() {
        return operator;
    }

    public void setOperator(Long operator) {
        this.operator = operator;
    }
}
