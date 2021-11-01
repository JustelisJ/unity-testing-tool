package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.services.VideoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class VideoServiceImplTest {

    private static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    private static final int INITIAL_POSTFIX = 1;
    private static final String TEST_VIDEO_NAME = "black screen.mp4";
    private static final String THIRD_TEST_VIDEO_NAME = "black screen(2).mp4";
    private static final String SLASH = "/";

    VideoService videoService = new VideoServiceImpl();

    File videoDirectory;

    @BeforeEach
    void setup() {
        videoDirectory = new File(VIDEO_DIRECTORY);
        videoDirectory.mkdirs();
    }

    @AfterEach
    void cleanUp() {
        deleteDirectory(VIDEO_DIRECTORY);
    }

    private void deleteDirectory(String directoryName) {
        File directory = new File(directoryName);
        String[] allFiles = directory.list();
        if (allFiles == null) {
            directory.delete();
            return;
        }
        for (File file : Arrays.stream(allFiles).map(s -> new File(directoryName + SLASH + s)).collect(Collectors.toList())) {
            if (file.isDirectory()) {
                deleteDirectory(file.getAbsolutePath());
            }
            file.delete();
        }
        directory.delete();
    }

    @Test
    void findMostRecentVideo_directoryDoesNotExist() {
        deleteDirectory(VIDEO_DIRECTORY);
        assertFalse(videoDirectory.exists());
        assertThrows(NoVideosException.class, () -> videoService.findMostRecentVideo());
    }

    @Test
    void findMostRecentVideo_noVideosInDirectory() {
        assertTrue(videoDirectory.exists());
        assertThrows(NoVideosException.class, () -> videoService.findMostRecentVideo());
    }

    @Test
    void findMostRecentVideo_findFromOneVideo() throws IOException {
        MultipartFile testVideo = new MockMultipartFile(TEST_VIDEO_NAME, new byte[0]);
        for (int i = 0; i < 1; i++) {
            saveFile(testVideo);
        }
        assertEquals(TEST_VIDEO_NAME, videoService.findMostRecentVideo());
    }

    @Test
    void findMostRecentVideo_findFromSeveralVideos() throws IOException, InterruptedException {
        MultipartFile testVideo = new MockMultipartFile(TEST_VIDEO_NAME, new byte[0]);
        for (int i = 0; i < 3; i++) {
            saveFile(testVideo);
            Thread.sleep(100);
        }
        assertEquals(THIRD_TEST_VIDEO_NAME, videoService.findMostRecentVideo());
    }

    private void saveFile(MultipartFile file) throws IOException {
        File convertFile = new File(VIDEO_DIRECTORY + file.getName());
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

    @Test
    void saveVideo_successfullySaves() {
        MultipartFile testFile = new MockMultipartFile(TEST_VIDEO_NAME, TEST_VIDEO_NAME, "video", new byte[0]);
        assertDoesNotThrow(() -> videoService.saveVideo(testFile, "Game name", "1.0"));
    }

}