package com.julio.restaurant_review.application.port.out.storage;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.Set;
import java.util.stream.Stream;

public interface FileStoragePort {
    void save(MultipartFile file);
    Resource load(String filename);
    Path loadFilePath(String filename);
    Stream<Path> loadAll(Set<String> filenames);
}
