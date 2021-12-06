package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.utils.CommonValidations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BuildServiceImplTest {

    private static final String VIDEO_DIRECTORY = "src\\main\\resources\\videos\\";
    private static final String SLASH = "/";
    public static final String GAME_NAME = "testGame";

    CommonValidations commonValidations = mock(CommonValidations.class);

    BuildService buildService = new BuildServiceImpl(commonValidations);

    @BeforeEach
    void setup() throws DirectoryDoesNotExistException {
        GameService gameService = new GameServiceImpl();
        File videoDirectory = new File(VIDEO_DIRECTORY);
        gameService.createNewGameDirectory(GAME_NAME);
        videoDirectory.mkdirs();

        //mockito mocks
        File gameDirectory = new File(VIDEO_DIRECTORY + GAME_NAME + SLASH);
        when(commonValidations.validateThatGameDirectoryExists(anyString())).thenReturn(gameDirectory);
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
    void getAllBuildForAGame_noBuilds() throws DirectoryDoesNotExistException {
        assertEquals(List.of(), buildService.getAllBuildForAGame(GAME_NAME));
    }

    @Test
    void getAllBuildForAGame_2Builds() throws DirectoryDoesNotExistException {
        buildService.createNewBuildInGameDirectory(GAME_NAME, "1.0");
        buildService.createNewBuildInGameDirectory(GAME_NAME, "1.1");
        assertEquals(List.of("1.0", "1.1"), buildService.getAllBuildForAGame(GAME_NAME));
    }

    @Test
    void createNewBuildInGameDirectory() throws DirectoryDoesNotExistException {
        buildService.createNewBuildInGameDirectory(GAME_NAME, "1.0");
        assertEquals(List.of("1.0"), buildService.getAllBuildForAGame(GAME_NAME));
    }

    @Test
    void createNewBuildInGameDirectory_alreadyExists() throws DirectoryDoesNotExistException {
        buildService.createNewBuildInGameDirectory(GAME_NAME, "1.0");
        assertThrows(DirectoryDoesNotExistException.class, () -> buildService.createNewBuildInGameDirectory(GAME_NAME, "1.0"));
    }
}