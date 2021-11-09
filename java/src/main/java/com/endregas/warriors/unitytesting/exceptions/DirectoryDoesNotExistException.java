package com.endregas.warriors.unitytesting.exceptions;

import java.io.IOException;

public class DirectoryDoesNotExistException extends IOException {

    public DirectoryDoesNotExistException(){
        super();
    }

    public DirectoryDoesNotExistException(String game, String build){
        super(String.format("Game directory %s/%s does not exist", game, build));
    }

}
