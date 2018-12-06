package construction_and_testing.public_transport_system.controller;

import construction_and_testing.public_transport_system.util.Image;
import construction_and_testing.public_transport_system.service.upload.ImageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;

@RestController
@RequestMapping("/api/image")
public class UploadController {

    private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    private ImageService imageService;

    /**
     * GET /api/image/{path}
     *
     * @param path that are requested
     * @return image content and format
     */
    @GetMapping("{path}")
    public ResponseEntity<Image> getImage(@PathVariable String path) {
        logger.info("Trying to get image {} at time {}.", path, Calendar.getInstance().getTime());
        return new ResponseEntity<>(imageService.getImage(path), HttpStatus.OK);
    }

    /**
     * POST /api/image
     *
     * @param image that needs to be saved
     * @return action result
     */
    @PostMapping(produces = "text/plain")
    public ResponseEntity<String> uploadImage(@RequestParam("image") MultipartFile image) {
        logger.info("Trying to upload image at time {}.", Calendar.getInstance().getTime());
        return new ResponseEntity<>(imageService.uploadImage(image), HttpStatus.OK);
    }



    @GetMapping()
    public ResponseEntity<String> getImage() {

        return new ResponseEntity<>("nesto", HttpStatus.OK);

    }
}