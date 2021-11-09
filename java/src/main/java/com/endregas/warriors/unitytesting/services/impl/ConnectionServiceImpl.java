package com.endregas.warriors.unitytesting.services.impl;

import com.endregas.warriors.unitytesting.services.ConnectionService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class ConnectionServiceImpl implements ConnectionService {

    public static final String VIDEO_DIRECTORY = "src/main/resources/videos/";

    @Override
    public Map<String, String> getGameWithLatestBuildMap() {
        Map<String, String> gameBuildMap = new HashMap<>();
        File[] gameDirectories = new File(VIDEO_DIRECTORY).listFiles(File::isDirectory);
        if (gameDirectories != null) {
            for (File gameDirectory : gameDirectories) {
                File[] buildDirectories = gameDirectory.listFiles(File::isDirectory);
                Optional<String> latestBuild = Arrays.stream(Objects.requireNonNull(buildDirectories))
                        .max(Comparator.comparing(File::lastModified))
                        .map(File::getName);
                if (latestBuild.isEmpty()) {
                    continue;
                }
                gameBuildMap.put(gameDirectory.getName(), latestBuild.get());
            }
        }
        return gameBuildMap;
    }

}
