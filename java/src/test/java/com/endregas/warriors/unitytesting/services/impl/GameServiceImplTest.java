package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.GameService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class GameServiceImplTest {

    private static final String VIDEO_DIRECTORY = "src\\main\\resources\\videos\\";
    private static final String SLASH = "/";

    GameService gameService = new GameServiceImpl();

    @BeforeEach
    void setup() {
        File videoDirectory = new File(VIDEO_DIRECTORY);
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
    void getAllGames_noGames() {
        assertEquals(List.of(), gameService.getAllGames());
    }

    @Test
    void getAllGames_2games() throws DirectoryDoesNotExistException {
        gameService.createNewGameDirectory("test1");
        gameService.createNewGameDirectory("test2");
        assertEquals(List.of("test1", "test2"), gameService.getAllGames());
    }

    @Test
    void createNewGameDirectory() throws DirectoryDoesNotExistException {
        gameService.createNewGameDirectory("test1");
        assertEquals(List.of("test1"), gameService.getAllGames());
    }

    @Test
    void createNewGameDirectory_alreadyExists() throws DirectoryDoesNotExistException {
        gameService.createNewGameDirectory("test1");
        assertThrows(DirectoryDoesNotExistException.class, () -> gameService.createNewGameDirectory("test1"));
    }
}