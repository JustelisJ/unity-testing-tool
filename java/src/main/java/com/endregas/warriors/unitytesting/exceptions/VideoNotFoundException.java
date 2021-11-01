package com.endregas.warriors.unitytesting.exceptions;

import java.io.IOException;

public class VideoNotFoundException extends IOException {

    public VideoNotFoundException(){
        super("Video could not be found");
    }

}
