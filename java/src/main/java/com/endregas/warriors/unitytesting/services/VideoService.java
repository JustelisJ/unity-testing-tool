package com.endregas.warriors.unitytesting.services;

import com.endregas.warriors.unitytesting.exceptions.NoVideosException;
import com.endregas.warriors.unitytesting.exceptions.VideoNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface VideoService {

    void saveVideo(MultipartFile file, String game, String build) throws IOException;

    String findMostRecentVideo() throws NoVideosException, VideoNotFoundException;

}
