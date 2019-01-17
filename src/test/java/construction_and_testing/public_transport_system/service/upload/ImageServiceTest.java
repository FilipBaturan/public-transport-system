package construction_and_testing.public_transport_system.service.upload;

import construction_and_testing.public_transport_system.util.GeneralException;
import construction_and_testing.public_transport_system.util.Image;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ImageServiceTest {

    @Autowired
    private ImageService imageService;

    private String destinationFolder;

    private File image;

    private void generateImage() throws IOException {
        if (this.image.createNewFile()) {
            ImageIO.write(new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY), "png",
                    new File(this.destinationFolder + "/black.png"));
        }
    }

    @Before
    public void setUp() throws Exception {
        this.destinationFolder = Paths.get(new File(".").getCanonicalPath(),
                "src", "test", "java", "resources", "static", "documents").toString();
        imageService.setDestinationFolder(this.destinationFolder);
        this.image = new File(this.destinationFolder + "/black.png");
    }


    /**
     * Test upload with images in folders
     */
    @Test
    public void testUploadImageNoPreviousImages() throws IOException {
        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY), "png", byteArray );
        byteArray.flush();
        byte[] imageInByte = byteArray.toByteArray();
        byteArray.close();

        String image =  imageService.uploadImage(new MockMultipartFile("image", "image.png",
                "multipart-form-data", imageInByte));
        assertThat(image).isEqualTo("img_1_1.png");
        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount + 1);
    }

    /**
     * Test upload with images in folders
     */
    @Test
    public void testUploadImageWithPreviousImages() throws IOException {
        this.generateImage();
        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        ImageIO.write(new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY), "png", byteArray );
        byteArray.flush();
        byte[] imageInByte = byteArray.toByteArray();
        byteArray.close();

        String image =  imageService.uploadImage(new MockMultipartFile("image", "image.png",
                "multipart-form-data", imageInByte));
        assertThat(image).isEqualTo("img_2_2.png");
        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount + 1);
    }

    /**
     * Test upload with empty image content
     */
    @Test(expected = GeneralException.class)
    public void testUploadImageWithEmptyContent() {
        imageService.uploadImage(new MockMultipartFile("image", new byte[0]));
    }


    /**
     * Test upload with invalid image format
     */
    @Test(expected = GeneralException.class)
    public void testUploadImageWithInvalidImageFormat() {
        imageService.uploadImage(new MockMultipartFile("image", "image.cl",
                "multipart-form-data",  new byte[1]));
    }

    /**
     * Test upload with invalid destination path
     */
    @Test(expected = GeneralException.class)
    public void testUploadImageInvalidPath() {
        imageService.setDestinationFolder("");
        imageService.uploadImage(new MockMultipartFile("image", "image.png",
                "multipart-form-data", new byte[1]));
    }

    /**
     * Test get black.png image
     */
    @Test
    public void testGetImage() throws Exception {
        this.generateImage();
        Image image = imageService.getImage("black.png");
        assertThat(image.getContent()).isEqualTo(Files.readAllBytes(Paths.get(this.image.getPath())));
        assertThat(image.getFormat()).isEqualTo("png");
    }

    /**
     * Test get image that does not exist
     */
    @Test(expected = GeneralException.class)
    public void testGetInvalidImage() throws Exception {
        this.generateImage();
        imageService.getImage("red.png");
    }

    /**
     * Test get image with null name
     */
    @Test(expected = GeneralException.class)
    public void testGetImageWithNullName() {
        imageService.getImage(null);
    }

    /**
     * Test get image with null name
     */
    @Test(expected = GeneralException.class)
    public void testGetImageWithEmptyName() {
        imageService.getImage("");
    }

    /**
     * Test get image with with invalid image format
     */
    @Test(expected = GeneralException.class)
    public void testGetImageWithInvalidImageFormat() {
        imageService.getImage("temp.cl");
    }


    @After
    public void removeImage() throws IOException {
        FileUtils.cleanDirectory(new File(this.destinationFolder));
    }
}