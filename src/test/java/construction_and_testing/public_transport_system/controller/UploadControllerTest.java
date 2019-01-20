package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.service.upload.ImageService;
import construction_and_testing.public_transport_system.util.Image;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class UploadControllerTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private ImageService imageService;

    private final String URL = "/api/image";

    private String destinationFolder;

    private String tempFolder;

    private File image;

    private File tempFile;

    private void generateImage() throws IOException {
        if (this.image.createNewFile()) {
            ImageIO.write(new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY), "png",
                    new File(this.destinationFolder + "/black.png"));
        }
    }

    private void generateTempImage() throws IOException {
        if (this.tempFile.createNewFile()) {
            ImageIO.write(new BufferedImage(320, 240, BufferedImage.TYPE_BYTE_GRAY), "png",
                    new File(this.tempFolder + "/black.png"));
        }
    }

    private FileSystemResource generateEmptyImage() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(this.tempFolder + "/empty.png"));
        out.write("");
        out.close();
        return new FileSystemResource(new File(this.tempFolder + "/empty.png"));
    }

    private FileSystemResource generateFile() throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(this.tempFolder + "/empty.txt"));
        out.write("dummy content");
        out.close();
        return new FileSystemResource(new File(this.tempFolder + "/empty.txt"));
    }

    @Before
    public void setUp() throws Exception {
        this.destinationFolder = Paths.get(new File(".").getCanonicalPath(),
                "src", "test", "java", "resources", "static", "documents").toString();
        this.tempFolder = Paths.get(new File(".").getCanonicalPath(),
                "src", "test", "java", "resources", "static", "temp").toString();
        File directory = new File(destinationFolder);
        if (!directory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
        }
        directory = new File(tempFolder);
        if (!directory.exists()) {
            //noinspection ResultOfMethodCallIgnored
            directory.mkdirs();
        }
        imageService.setDestinationFolder(this.destinationFolder);
        this.image = new File(this.destinationFolder + "/black.png");
        this.tempFile = new File(this.tempFolder + "/black.png");
    }

    /**
     * Test upload with no images in folders
     */
    @Test
    public void testUploadImageWithNoPreviousImages() throws Exception {
        this.generateTempImage();
        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("image", new FileSystemResource(new File(this.tempFolder + "/black.png")));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> result = testRestTemplate
                .exchange(this.URL, HttpMethod.POST, entity, String.class, "");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        String body = result.getBody();
        assertThat(body).isNotNull();

        assertThat(body).isEqualTo("img_1_1.png");
        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount + 1);
    }

    /**
     * Test upload with no images in folders
     */
    @Test
    public void testUploadImageWithPreviousImages() throws Exception {
        this.generateImage();
        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("image", new FileSystemResource(new File(this.destinationFolder + "/black.png")));


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> result = testRestTemplate
                .exchange(this.URL, HttpMethod.POST, entity, String.class, "");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        String body = result.getBody();
        assertThat(body).isNotNull();

        assertThat(body).isEqualTo("img_2_2.png");
        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount + 1);
    }

    /**
     * Test upload with empty image content
     */
    @Test
    public void testUploadImageWithEmptyContent() throws IOException {

        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("image", this.generateEmptyImage());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> result = testRestTemplate
                .exchange(this.URL, HttpMethod.POST, entity, String.class, "");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount);
    }

    /**
     * Test upload with empty image content
     */
    @Test
    public void testUploadImageBadFormat() throws IOException {

        int beforeCount = Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length;

        LinkedMultiValueMap<String, Object> parameters = new LinkedMultiValueMap<>();
        parameters.add("image", this.generateFile());


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(parameters, headers);

        ResponseEntity<String> result = testRestTemplate
                .exchange(this.URL, HttpMethod.POST, entity, String.class, "");

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String body = result.getBody();
        assertThat(body).isNotNull();

        assertThat(body).isEqualTo("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF");
        assertThat(Objects.requireNonNull(new File(this.destinationFolder).listFiles()).length)
                .isEqualTo(beforeCount);
    }

    /**
     * Test get black.png image
     */
    @Test
    public void getImage() throws IOException {
        this.generateImage();
        ResponseEntity<Image> result = testRestTemplate.getForEntity(this.URL + "/black.png", Image.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);

        Image body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body.getContent()).isEqualTo(Files.readAllBytes(Paths.get(this.image.getPath())));
        assertThat(body.getFormat()).isEqualTo("png");
    }

    /**
     * Test get image that does not exist
     */
    @Test
    public void getImageWithEmptyName() throws IOException {
        this.generateImage();
        ResponseEntity<String> result = testRestTemplate.getForEntity(this.URL + "/red.png", String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);

        String body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Can not get image.");
    }

    /**
     * Test get image with bad format
     */
    @Test
    public void getImageWithBadFormat() throws IOException {
        this.generateImage();
        ResponseEntity<String> result = testRestTemplate.getForEntity(this.URL + "/black.cl", String.class);

        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        String body = result.getBody();
        assertThat(body).isNotNull();
        assertThat(body).isEqualTo("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF");
    }

    @After
    public void removeImage() throws IOException {
        FileUtils.cleanDirectory(new File(this.destinationFolder));
        FileUtils.cleanDirectory(new File(this.tempFolder));
    }
}