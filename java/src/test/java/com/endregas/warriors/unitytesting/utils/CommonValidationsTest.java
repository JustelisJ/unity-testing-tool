package com.endregas.warriors.unitytesting.utils;

import com.endregas.warriors.unitytesting.common.Utils;
import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.services.impl.BuildServiceImpl;
import com.endregas.warriors.unitytesting.services.impl.GameServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CommonValidationsTest {

    public static final String VIDEO_DIRECTORY = "src\\main\\resources\\videos\\";


    CommonValidations commonValidations = new CommonValidations();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        Utils.deleteDirectory(VIDEO_DIRECTORY);
    }

    @Test
    void validateDirectoriesExist_exists() throws DirectoryDoesNotExistException {
        GameService gameService = new GameServiceImpl();
        gameService.createNewGameDirectory("asd");
        assertDoesNotThrow(() -> commonValidations.validateDirectoriesExist(new File(VIDEO_DIRECTORY)));
    }

    @Test
    void validateDirectoriesExist_doesntExist() {
        assertThrows(NoVideosException.class, () -> commonValidations.validateDirectoriesExist(new File(VIDEO_DIRECTORY)));
    }

    @Test
    void validateThatGameBuildDirectoryExists_true() throws DirectoryDoesNotExistException {
        GameService gameService = new GameServiceImpl();
        gameService.createNewGameDirectory("asd");
        BuildService buildService = new BuildServiceImpl(commonValidations);
        buildService.createNewBuildInGameDirectory("asd", "1.0");
        assertDoesNotThrow(() -> commonValidations.validateThatGameBuildDirectoryExists("asd", "1.0"));
    }

    @Test
    void validateThatGameBuildDirectoryExists_false() {
        assertThrows(DirectoryDoesNotExistException.class, () -> commonValidations.validateThatGameBuildDirectoryExists("asd", "1.0"));
    }

    @Test
    void validateThatGameDirectoryExists_true() throws DirectoryDoesNotExistException {
        GameService gameService = new GameServiceImpl();
        gameService.createNewGameDirectory("asd");
        assertDoesNotThrow(() -> commonValidations.validateThatGameDirectoryExists("asd"));
    }

    @Test
    void validateThatGameDirectoryExists_false() {
        assertThrows(DirectoryDoesNotExistException.class, () -> commonValidations.validateThatGameDirectoryExists("asd"));
    }
}