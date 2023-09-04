package com.competa.competademo.service.impl;

import com.competa.competademo.entity.ImageInfo;
import com.competa.competademo.repository.ImageInfoRepository;
import com.competa.competademo.service.FilesStorageService;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.stream.Stream;
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("./uploads");
    private final ImageInfoRepository imageInfoRepository;

    public FilesStorageServiceImpl(ImageInfoRepository imageInfoRepository) {
        this.imageInfoRepository = imageInfoRepository;
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

    @Override
    public ImageInfo save(MultipartFile file) {
        final Path path = root.resolve(UUID.randomUUID() + "." + file.getOriginalFilename().split("\\.")[1]);
        try {
            Files.copy(file.getInputStream(), path);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException("A file of that name already exists.");
            }
            throw new RuntimeException(e.getMessage());
        }

       final ImageInfo entityToSave = ImageInfo.builder()
                .url(path.toString())
                .name(file.getOriginalFilename())
                .build();

       return imageInfoRepository.save(entityToSave);
    }

    @Override
    public Resource load(String filename) {
        try {
            Path file = root.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public boolean delete(String filename) {
        try {
            Path file = root.resolve(filename);
            return Files.deleteIfExists(file);
        } catch (IOException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(root.toFile());
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
    @Override
    public String getBase64Image(Path path){
        //final var filePath = Path.of(user.getProfileAvatar().getUrl());

        try {
            final UrlResource resource = new UrlResource(path.toUri());
            if (resource.exists() && resource.isReadable()) {
                byte[] imageBytes = Files.readAllBytes(path);
                return Base64Utils.encodeToString(imageBytes);
            }
        } catch (final IOException e) {
            throw new RuntimeException("Error reading the avatar");
        }
        return "";
    }

}

