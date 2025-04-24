package com.julio.restaurant_review.application.util;

import com.julio.restaurant_review.adapter.in.rest.controllers.FilesController;
import com.julio.restaurant_review.application.dtos.ImageInfoDTO;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.nio.file.Path;

public class ImageMapper {
    public static ImageInfoDTO getImageInfoDTO(Path path) {
        String filename = path.getFileName().toString();
        String url = MvcUriComponentsBuilder
                .fromMethodName(FilesController.class, "getFile", path.getFileName().toString()).build().toString();
        return new ImageInfoDTO(filename, url);
    }
}
