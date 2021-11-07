package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.BuildService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BuildServiceImpl implements BuildService {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    public static final String SLASH = "/";

    @Override
    public List<String> getAllBuildForAGame(String game) throws DirectoryDoesNotExistException {
        File gameDirectory = new File(VIDEO_DIRECTORY + game);
        validateDirectoryExists(game, gameDirectory);
        File[] allFiles = gameDirectory.listFiles();
        if (allFiles != null) {
            return Arrays.stream(allFiles)
                    .filter(File::isDirectory)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    private void validateDirectoryExists(String game, File gameDirectory) throws DirectoryDoesNotExistException {
        if (!gameDirectory.exists()) {
            throw new DirectoryDoesNotExistException(game, "");
        }
    }

    @Override
    public String createNewBuildInGameDirectory(String game, String build) throws DirectoryDoesNotExistException {
        File buildDirectory = new File(VIDEO_DIRECTORY + game + SLASH + build + SLASH);
        if (!buildDirectory.mkdirs()) {
            throw new DirectoryDoesNotExistException(game, build);
        }
        return buildDirectory.getName();
    }
}
