package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.exceptions.VideoNotFoundException;
import com.endregas.warriors.unitytesting.services.VideoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
public class VideoServiceImpl implements VideoService {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    public static final int INITIAL_POSTFIX = 1;
    public static final String SLASH = "/";

    @Override
    public void saveVideo(MultipartFile file, String game, String build) throws IOException {
        File directory = createDirectoryIfDoesntExist(game, build);
        saveFile(directory, file);
    }

    @Override
    public String findMostRecentVideo() throws NoVideosException, VideoNotFoundException {
        File videoDirectory = new File(VIDEO_DIRECTORY);
        validateThatVideoDirectoriesExist(videoDirectory);
        String lastModifiedVideoFile = getLastModifiedVideoFile(videoDirectory);
        log.info(String.format("Last modified video file is %s", lastModifiedVideoFile));
        return lastModifiedVideoFile;
    }

    private void validateThatVideoDirectoriesExist(File videoDirectory) throws NoVideosException {
        if (!videoDirectory.exists()) {
            videoDirectory.mkdirs();
            throw new NoVideosException();
        }
        File[] allFiles = videoDirectory.listFiles();
        if (allFiles == null) {
            throw new NoVideosException();
        }
    }

    private String getLastModifiedVideoFile(File videoDirectory) throws VideoNotFoundException {
        List<File> allVideoFiles = getAllVideos(videoDirectory);
        Optional<String> optionalName = allVideoFiles.stream()
                .max(Comparator.comparing(File::lastModified))
                .map(File::getPath);
        return optionalName.orElseThrow(VideoNotFoundException::new);
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
        long startTime = System.nanoTime();
        try (InputStream in = file.getInputStream(); FileOutputStream out = new FileOutputStream(convertFile)) {
            byte[] buffer = new byte[4096]; //Buffer size, Usually 1024-4096
            int len;
            while ((len = in.read(buffer, 0, buffer.length)) > 0) {
                out.write(buffer, 0, len);
            }
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        log.info(String.format("File %s (%d bytes) was uploaded in %d milliseconds", file.getName(), file.getSize(), duration));
    }

    private File createDirectoryIfDoesntExist(String game, String build) {
        File directory = new File(VIDEO_DIRECTORY + game + SLASH + build + SLASH);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory;
    }

    private File addSuffix(File convertFile, int suffix) throws IOException {
        File renamedFile = new File(convertFile.toURI().getPath().replaceFirst(".mp4", "(" + suffix + ").mp4"));
        if (!renamedFile.createNewFile()) {
            return addSuffix(convertFile, suffix + 1);
        }
        return renamedFile;
    }
}
