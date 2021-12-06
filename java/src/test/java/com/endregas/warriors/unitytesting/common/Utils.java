package com.endregas.warriors.unitytesting.common;

import java.io.File;
import java.util.Arrays;
import java.util.stream.Collectors;

public class Utils {

    private static final String SLASH = "/";

    public static void deleteDirectory(String directoryName) {
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

}
