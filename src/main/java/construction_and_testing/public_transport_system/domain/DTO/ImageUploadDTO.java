package construction_and_testing.public_transport_system.domain.DTO;

public class ImageUploadDTO {

    Long id;

    String image;

    public ImageUploadDTO() {
    }

    public ImageUploadDTO(Long id, String image) {
        this.id = id;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public String getImage() {
        return image;
    }
}
