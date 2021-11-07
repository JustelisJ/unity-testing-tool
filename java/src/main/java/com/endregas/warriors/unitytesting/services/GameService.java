package com.endregas.warriors.unitytesting.services;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;

import java.util.List;

public interface GameService {

    List<String> getAllGames();

    String createNewGameDirectory(String game) throws DirectoryDoesNotExistException;

}
