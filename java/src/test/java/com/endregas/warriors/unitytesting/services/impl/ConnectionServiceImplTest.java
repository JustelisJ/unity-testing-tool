package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.ConnectionService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.utils.CommonValidations;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConnectionServiceImplTest {

    private static final String VIDEO_DIRECTORY = "src\\main\\resources\\videos\\";
    private static final String SLASH = "/";
    public static final String GAME_NAME_1 = "testGame";
    public static final String GAME_NAME_2 = "testGame2";
    public static final String BUILD_1 = "1";
    public static final String BUILD_2 = "2";

    @Mock
    CommonValidations commonValidations;

    final GameService gameService = new GameServiceImpl();
    final BuildService buildService = new BuildServiceImpl(commonValidations);
    final ConnectionService connectionService = new ConnectionServiceImpl();

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
    void getGameWithLatestBuildMap_noGameDirectories() {
        assertEquals(Map.of(), connectionService.getGameWithLatestBuildMap());
    }

    @Test
    void getGameWithLatestBuildMap_noBuildDirectories() throws DirectoryDoesNotExistException {
        gameService.createNewGameDirectory(GAME_NAME_1);
        assertEquals(Map.of(), connectionService.getGameWithLatestBuildMap());
    }

    @Test
    void getGameWithLatestBuildMap_oneGameAndTwoBuildDirectories() throws DirectoryDoesNotExistException, InterruptedException {
        gameService.createNewGameDirectory(GAME_NAME_1);
        buildService.createNewBuildInGameDirectory(GAME_NAME_1, BUILD_1);
        Thread.sleep(100);
        buildService.createNewBuildInGameDirectory(GAME_NAME_1, BUILD_2);
        assertEquals(Map.of(GAME_NAME_1, BUILD_2), connectionService.getGameWithLatestBuildMap());
    }

    @Test
    void getGameWithLatestBuildMap_twoGameAndTwoBuildDirectories() throws DirectoryDoesNotExistException, InterruptedException {
        gameService.createNewGameDirectory(GAME_NAME_1);
        buildService.createNewBuildInGameDirectory(GAME_NAME_1, BUILD_1);
        Thread.sleep(100);
        buildService.createNewBuildInGameDirectory(GAME_NAME_1, BUILD_2);
        gameService.createNewGameDirectory(GAME_NAME_2);
        buildService.createNewBuildInGameDirectory(GAME_NAME_2, BUILD_1);
        assertEquals(Map.of(GAME_NAME_1, BUILD_2, GAME_NAME_2, BUILD_1), connectionService.getGameWithLatestBuildMap());
    }
}