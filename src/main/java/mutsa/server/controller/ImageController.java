package mutsa.server.controller;

import lombok.RequiredArgsConstructor;
import mutsa.server.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> testImage(@RequestParam("file") MultipartFile file) {
        String path = imageService.uploadFile(file);
        return ResponseEntity.ok(path);
    }

}
