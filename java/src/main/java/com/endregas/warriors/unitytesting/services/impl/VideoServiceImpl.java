package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.exceptions.VideoNotFoundException;
import com.endregas.warriors.unitytesting.model.dto.RecentVideoDTO;
import com.endregas.warriors.unitytesting.services.VideoService;
import com.endregas.warriors.unitytesting.utils.CommonValidations;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    public static final int INITIAL_POSTFIX = 1;
    public static final String SLASH = "/";

    final CommonValidations commonValidations;

    @Override
    public List<String> getAllVideosForGameAndBuild(String game, String build) throws NoVideosException {
        File buildDirectory = new File(VIDEO_DIRECTORY + game + SLASH + build + SLASH);
        commonValidations.validateDirectoriesExist(buildDirectory);
        File[] allFiles = buildDirectory.listFiles();
        if (allFiles != null) {
            return Arrays.stream(allFiles)
                    .filter(File::isFile)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    @Override
    public void saveVideo(MultipartFile file, String game, String build) throws IOException {
        File directory = commonValidations.validateThatGameBuildDirectoryExists(game, build);
        saveFile(directory, file);
    }

    @Override
    public String findMostRecentVideo() throws NoVideosException, VideoNotFoundException {
        File videoDirectory = new File(VIDEO_DIRECTORY);
        commonValidations.validateDirectoriesExist(videoDirectory);
        return getLastModifiedVideoFile(videoDirectory);
    }

    private String getLastModifiedVideoFile(File videoDirectory) throws VideoNotFoundException {
        List<File> allVideoFiles = getAllVideos(videoDirectory);
        Optional<File> optionalName = allVideoFiles.stream()
                .max(Comparator.comparing(File::lastModified));
        File lastModifiedVideo = optionalName.orElseThrow(VideoNotFoundException::new);
        RecentVideoDTO mostRecentVideo = new RecentVideoDTO(lastModifiedVideo.getName().replaceFirst(".mp4", ""),
                lastModifiedVideo.getPath().replace('\\', '/').replaceFirst("src/main/resources/", ""));
        return new Gson().toJson(mostRecentVideo);
    }

    private List<File> getAllVideos(File videoDirectory) {
        List<File> allVideoFiles = new ArrayList<>();
        Deque<File> allDirectories = new ArrayDeque<>();
        allDirectories.push(videoDirectory);
        while (!allDirectories.isEmpty()) {
            File directory = allDirectories.pop();
            if (directory.listFiles() != null) {
                List<File> allFiles = List.of(Objects.requireNonNull(directory.listFiles()));
                for (File file : allFiles) {
                    if (file.isDirectory()) {
                        allDirectories.push(file);
                    }
                    if (file.isFile()) {
                        allVideoFiles.add(file);
                    }
                }
            }
        }
        return allVideoFiles;
    }

    private void saveFile(File directory, MultipartFile file) throws IOException {
        File convertFile = new File(directory + SLASH + file.getOriginalFilename());
        if (!convertFile.createNewFile()) {
            convertFile = addSuffix(convertFile, INITIAL_POSTFIX);
        }
        try (InputStream in = file.getInputStream(); FileOutputStream out = new FileOutputStream(convertFile)) {
            byte[] buffer = new byte[4096]; //Buffer size, Usually 1024-4096
            int len;
            while ((len = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, len);
            }
        }
    }

    private File addSuffix(File convertFile, int suffix) throws IOException {
        File renamedFile = new File(convertFile.toURI().getPath().replaceFirst(".mp4", "(" + suffix + ").mp4"));
        if (!renamedFile.createNewFile()) {
            return addSuffix(convertFile, suffix + 1);
        }
        return renamedFile;
    }
}
