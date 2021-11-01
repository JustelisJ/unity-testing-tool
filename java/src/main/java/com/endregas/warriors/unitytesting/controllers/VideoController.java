package com.endregas.warriors.unitytesting.controllers;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.exceptions.VideoNotFoundException;
import com.endregas.warriors.unitytesting.services.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class VideoController {

    final VideoService videoService;

    @PostMapping(value = "/video", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> saveVideo(@RequestBody @NotNull MultipartFile file,
                                            @RequestParam(name = "Game") @NotNull @Size(max = 50) String game,
                                            @RequestParam(name = "Build") @NotNull @Size(max = 20) String build) throws IOException {
        videoService.saveVideo(file, game, build);
        return ResponseEntity.ok().body("File is uploaded successfully");
    }

    @GetMapping(value = "/video/recent")
    public ResponseEntity<String> getMostRecentVideo() throws NoVideosException, VideoNotFoundException {
        return ResponseEntity.ok().body(videoService.findMostRecentVideo());
    }

}
