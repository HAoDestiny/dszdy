package net.gzhqlf.dszdy.controller;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Paths;

/**
 * Created by DESTINY on 2017/10/23.
 */

@RestController
public class FileController {

    @Value("${dszdy.project.imagesPath}")
    private String ROOT;

    private final ResourceLoader resourceLoader;

    @Autowired
    public FileController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/images/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {

        Resource resource = resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString());

        if (resource.exists()) {
            return ResponseEntity.ok(resource);
        }

        return ResponseEntity.ok(resourceLoader.getResource("classpath:static/images/face.png"));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/images/{width}/{height}/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable int width, @PathVariable int height, @PathVariable String filename,
                                     OutputStream outputStream) {

        Resource resource = resourceLoader.getResource("file:" + Paths.get(ROOT, filename).toString());

        if (!resource.exists()) {
            return ResponseEntity.ok(resourceLoader.getResource("classpath:static/images/face.png"));
        }

        //压缩至指定图片尺寸（例如：横400高300），保持图片不变形，多余部分裁剪掉
        try {
            File imageFile = resource.getFile();
            BufferedImage image = ImageIO.read(imageFile);
            Thumbnails.Builder<BufferedImage> builder = null;

            int imageWidth = image.getWidth();
            int imageHeight = image.getHeight();
            if ((float) height / width != (float) imageWidth / imageHeight) {
                if (imageWidth > imageHeight) {
                    image = Thumbnails.of(imageFile).height(height).asBufferedImage();
                } else {
                    image = Thumbnails.of(imageFile).width(width).asBufferedImage();
                }
                builder = Thumbnails.of(image).sourceRegion(Positions.CENTER, width, height).size(width, height);
            } else {
                builder = Thumbnails.of(image).size(width, height);
            }
            image = builder.outputFormat("jpg").asBufferedImage();

            ImageIO.write(image, "jpg", outputStream);

            return null;

        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }

    }
}
