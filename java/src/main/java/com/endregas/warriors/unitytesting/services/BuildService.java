package com.endregas.warriors.unitytesting.services;

import com.endregas.warriors.unitytesting.exceptions.DirectoryDoesNotExistException;

import java.util.List;

public interface BuildService {

    List<String> getAllBuildForAGame(String game) throws DirectoryDoesNotExistException;

    String createNewBuildInGameDirectory(String game, String build) throws DirectoryDoesNotExistException;

}
