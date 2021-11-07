package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;
import com.endregas.warriors.unitytesting.services.GameService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";
    public static final String SLASH = "/";

    @Override
    public List<String> getAllGames() {
        File videoDirectory = new File(VIDEO_DIRECTORY);
        File[] allFiles = videoDirectory.listFiles();
        if (allFiles != null) {
            return Arrays.stream(allFiles)
                    .filter(File::isDirectory)
                    .map(File::getName)
                    .collect(Collectors.toList());
        } else {
            return List.of();
        }
    }

    @Override
    public String createNewGameDirectory(String game) throws DirectoryDoesNotExistException {
        File gameDirectory = new File(VIDEO_DIRECTORY + game + SLASH);
        if (!gameDirectory.mkdirs()) {
            throw new DirectoryDoesNotExistException(game, "");
        }
        return gameDirectory.getName();
    }
}
