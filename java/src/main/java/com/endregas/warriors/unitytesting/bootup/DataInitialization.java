package com.endregas.warriors.unitytesting.bootup;

import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.services.BugService;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.services.VideoService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Component
@AllArgsConstructor
@Profile("local")
@Slf4j
public class DataInitialization {

    public static final String GAME_1 = "game1";
    public static final String GAME_2 = "game2";
    public static final String GAME_3 = "game3";
    public static final String BUILD_10 = "1.0";
    public static final String BUILD_20 = "2.0";
    public static final String BUILD_TEST = "testBuild";
    public static final String VIDEO_NAME = "black screen";
    public static final String BUG_NOTES_1 = "test bug";
    public static final String BUG_NOTES_2 = "test bug2";

    GameService gameService;
    BuildService buildService;
    VideoService videoService;
    BugService bugService;

    @PostConstruct
    public void initializeTestData() throws IOException {
        log.info("Initializing test data");
        //Make 3 games
        gameService.createNewGameDirectory(GAME_1);
        gameService.createNewGameDirectory(GAME_2);
        gameService.createNewGameDirectory(GAME_3);

        //Add 2 builds to game1
        buildService.createNewBuildInGameDirectory(GAME_1, BUILD_10);
        buildService.createNewBuildInGameDirectory(GAME_1, BUILD_20);
        //Add 1 build to game2
        buildService.createNewBuildInGameDirectory(GAME_2, BUILD_TEST);

        InputStream videoFileByteStream = new FileInputStream("src/test/resources/videos/FunGame/1.0/black screen.mp4");
        MultipartFile testVideo = new MockMultipartFile("black screen.mp4", "black screen.mp4", "video/mp4", videoFileByteStream);

        //Add videos to all build directories
        videoService.saveVideo(testVideo, GAME_1, BUILD_10);
        videoService.saveVideo(testVideo, GAME_1, BUILD_20);
        videoService.saveVideo(testVideo, GAME_2, BUILD_TEST);

        //Add bugs to videos
        bugService.reportABug(new BugReportDTO(GAME_1, BUILD_10, VIDEO_NAME, 5L, BUG_NOTES_1));
        bugService.reportABug(new BugReportDTO(GAME_1, BUILD_10, VIDEO_NAME, 30L, BUG_NOTES_2));

        bugService.reportABug(new BugReportDTO(GAME_1, BUILD_20, VIDEO_NAME, 5L, BUG_NOTES_1));
        bugService.reportABug(new BugReportDTO(GAME_1, BUILD_20, VIDEO_NAME, 30L, BUG_NOTES_2));

        bugService.reportABug(new BugReportDTO(GAME_2, BUILD_TEST, VIDEO_NAME, 5L, BUG_NOTES_1));
        bugService.reportABug(new BugReportDTO(GAME_2, BUILD_TEST, VIDEO_NAME, 30L, BUG_NOTES_2));
    }

}
