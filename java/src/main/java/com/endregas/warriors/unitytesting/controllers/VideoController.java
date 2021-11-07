package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.exceptions.VideoNotFoundException;
import com.endregas.warriors.unitytesting.services.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class VideoController {

    final VideoService videoService;

    @GetMapping(value = "/video")
    public ResponseEntity<List<String>> getAllVideos(
                                            @RequestParam(name = "game") @NotNull @Size(max = 50) String game,
                                            @RequestParam(name = "build") @NotNull @Size(max = 20) String build) throws NoVideosException {
        List<String> allVideoNames = videoService.getAllVideosForGameAndBuild(game, build);
        log.info("All videos fetched for {}/{}: {}", game, build, allVideoNames);
        return ResponseEntity.ok().body(allVideoNames);
    }

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveVideo(@RequestBody @NotNull MultipartFile file,
                                            @RequestParam(name = "game") @NotNull @Size(max = 50) String game,
                                            @RequestParam(name = "build") @NotNull @Size(max = 20) String build) throws IOException {
        long startTime = System.nanoTime();
        videoService.saveVideo(file, game, build);
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        log.info(String.format("File %s (%d bytes) was uploaded in %d milliseconds", file.getName(), file.getSize(), duration));
        return ResponseEntity.ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "/video/recent")
    public ResponseEntity<String> getMostRecentVideo() throws NoVideosException, VideoNotFoundException {
        String lastModifiedVideoFile = videoService.findMostRecentVideo();
        log.info(String.format("Last modified video file is %s", lastModifiedVideoFile));
        return ResponseEntity.ok().body(lastModifiedVideoFile);
    }

}
