package com.endregas.warriors.unitytesting.utils;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class CommonValidations {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    public static final String SLASH = "/";

    public void validateDirectoriesExist(File videoDirectory) throws NoVideosException {
        if (!videoDirectory.exists()) {
            throw new NoVideosException();
        }
        File[] allFiles = videoDirectory.listFiles();
        if (allFiles == null) {
            throw new NoVideosException();
        }
    }

    public File validateThatGameBuildDirectoryExists(String game, String build) throws DirectoryDoesNotExistException {
        File directory = new File(VIDEO_DIRECTORY + game + SLASH + build + SLASH);
        if (!directory.exists()) {
            throw new DirectoryDoesNotExistException(game, build);
        }
        return directory;
    }

    public File validateThatGameDirectoryExists(String game) throws DirectoryDoesNotExistException {
        File directory = new File(VIDEO_DIRECTORY + game + SLASH);
        if (!directory.exists()) {
            throw new DirectoryDoesNotExistException(game, "");
        }
        return directory;
    }

}
