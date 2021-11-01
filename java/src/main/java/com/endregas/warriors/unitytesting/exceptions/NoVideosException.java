package com.endregas.warriors.unitytesting.exceptions;

import java.io.IOException;

public class NoVideosException extends IOException {
    public NoVideosException(){
        super("There are no videos");
    }
}
