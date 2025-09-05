package mutsa.server.service;

import lombok.RequiredArgsConstructor;
import mutsa.server.config.AwsS3Client;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final AwsS3Client awsS3Client;

    public String uploadFile(MultipartFile image) {

        UUID imageId = UUID.randomUUID();
        Long fileSize = image.getSize();
        String filePath = "images/" + imageId + ".jpg";

        try{
            return awsS3Client.uploadFile(image, filePath);
        } catch (BadRequestException e) {
            throw new RuntimeException(e);
        }
    }
}
