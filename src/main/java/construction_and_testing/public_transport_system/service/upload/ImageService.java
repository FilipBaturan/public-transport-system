package construction_and_testing.public_transport_system.service.upload;

import construction_and_testing.public_transport_system.util.GeneralException;
import construction_and_testing.public_transport_system.util.Image;
import construction_and_testing.public_transport_system.util.UploadResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ImageService {

    private List<String> imageFormats = new ArrayList<String>() {{
        add("JPG");
        add("PNG");
        add("GIF");
        add("TIFF");
        add("BMP");
    }};

    private String destinationFolder;

    public ImageService() {
        try {
            destinationFolder = Paths.get(new File(".").getCanonicalPath(),
                    "src", "main", "resources", "static", "documents").toString();
        } catch (IOException e) {
            throw new GeneralException("Upload service is broken!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * @param image that needs to be stored
     * @return image path
     */
    public String uploadImage(MultipartFile image) {
        if (image.isEmpty()) {
            throw new GeneralException("Please select file to upload", HttpStatus.NO_CONTENT);
        }
        if (image.getOriginalFilename() == null || this.getExtension(image.getOriginalFilename()) == null) {
            throw new GeneralException("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            UploadResponse result = this.generateName(image.getOriginalFilename());
            Path path = Paths.get(result.getPath());
            Files.write(path, image.getBytes());
            return result.getName();
        } catch (IOException e) {
            throw new GeneralException("Image upload failed.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param image path
     * @return image content
     */
    public Image getImage(String image) {
        if (image == null || image.isEmpty()) {
            throw new GeneralException("Requested image does not exist.", HttpStatus.NOT_FOUND);
        }
        String extension = this.getExtension(image);
        if (extension == null) {
            throw new GeneralException("Wrong image format. Acceptable formats are: GIF, JPG, PNG, BMP, TIFF",
                    HttpStatus.BAD_REQUEST);
        }
        try {
            String path = this.destinationFolder;
            if (this.checkDirectory(path) == null) {
                throw new GeneralException("Can not get image.", HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new Image(Files.readAllBytes(Paths.get(path, image)), extension);
        } catch (IOException e) {
            throw new GeneralException("Can not get image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * @param imageFile that needs to be checked
     * @return is acceptable image format
     */
    private String getExtension(String imageFile) {
        String[] tokens = imageFile.split("\\.");
        if (imageFormats.contains(tokens[tokens.length - 1].toUpperCase())) {
            return tokens[tokens.length - 1];
        } else {
            return null;
        }
    }


    /**
     * Tries to create directory if does not exist
     *
     * @param path to image directory
     * @return File to image directory
     */
    private File checkDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                return null;
            }
        }
        return directory;
    }

    /**
     * @param imageFile that need be processed
     * @return full path of generated name
     */
    private UploadResponse generateName(String imageFile) {

        String[] tokens = imageFile.split("\\.");
        String path = this.destinationFolder;
        File directory = this.checkDirectory(path);
        if (directory == null) {
            throw new GeneralException("Can not upload image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        File[] files = directory.listFiles();
        if (files == null || files.length == 0) {
            return new UploadResponse(Paths.get("img_1_1." + tokens[tokens.length - 1]).toString(),
                    Paths.get(path, "img_1_1." + tokens[tokens.length - 1]).toString());
        } else {
            int count = files.length;
            return new UploadResponse(Paths.get("img_" + ++count + "_" + count +
                    "." + tokens[tokens.length - 1]).toString(),
                    Paths.get(path, "img_" + count + "_" + count + "."
                            + tokens[tokens.length - 1]).toString());
        }
    }

    public void setDestinationFolder(String destinationFolder) {
        this.destinationFolder = destinationFolder;
    }

}
