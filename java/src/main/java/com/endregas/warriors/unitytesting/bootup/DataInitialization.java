package com.endregas.warriors.unitytesting.bootup;

import com.endregas.warriors.unitytesting.exceptions.DuplicatePlayRunReportException;
import com.endregas.warriors.unitytesting.model.dto.BugReportDTO;
import com.endregas.warriors.unitytesting.model.dto.PlayRunReportDTO;
import com.endregas.warriors.unitytesting.model.utils.TimeInterval;
import com.endregas.warriors.unitytesting.services.BuildService;
import com.endregas.warriors.unitytesting.services.GameService;
import com.endregas.warriors.unitytesting.services.PlayRunService;
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
import java.util.List;

@Component
@AllArgsConstructor
@Profile("test")
@Slf4j
public class DataInitialization {

    public static final String GAME_1 = "game1";
    public static final String GAME_2 = "game2";
    public static final String GAME_3 = "game3";
    public static final String BUILD_10 = "1.0";
    public static final String BUILD_20 = "2.0";
    public static final String BUILD_TEST = "testBuild";
    public static final String VIDEO_NAME = "black screen.mp4";

    GameService gameService;
    BuildService buildService;
    VideoService videoService;
    PlayRunService playRunService;

    @PostConstruct
    public void initializeTestData() throws IOException, DuplicatePlayRunReportException {
        log.info("Initializing test data");

        try {
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
            MultipartFile testVideo = new MockMultipartFile(VIDEO_NAME, VIDEO_NAME, "video/mp4", videoFileByteStream);

            //Add videos to all build directories
            videoService.saveVideo(testVideo, GAME_1, BUILD_10);
            videoService.saveVideo(testVideo, GAME_1, BUILD_20);
            videoService.saveVideo(testVideo, GAME_2, BUILD_TEST);

            //Add reports to videos
            BugReportDTO bugReport1 = new BugReportDTO("first bug", "something happened", new TimeInterval(2.34, 6.34));
            BugReportDTO bugReport2 = new BugReportDTO("second bug", "something else happened", new TimeInterval(10, 20));
            BugReportDTO bugReport3 = new BugReportDTO("third bug", "something happened", new TimeInterval(5, 12));
            playRunService.saveReport(PlayRunReportDTO.builder()
                    .gameRef(GAME_1)
                    .buildRef(BUILD_10)
                    .videoRef(VIDEO_NAME)
                    .bugReports(List.of(bugReport1))
                    .build());
            playRunService.saveReport(PlayRunReportDTO.builder()
                    .gameRef(GAME_1)
                    .buildRef(BUILD_20)
                    .videoRef(VIDEO_NAME)
                    .bugReports(List.of(bugReport1, bugReport3))
                    .build());
            playRunService.saveReport(PlayRunReportDTO.builder()
                    .gameRef(GAME_2)
                    .buildRef(BUILD_TEST)
                    .videoRef(VIDEO_NAME)
                    .bugReports(List.of(bugReport1, bugReport2, bugReport3))
                    .build());
        } catch (Exception e) {

        }
    }

}
