package io.podarkes.controllers;

import io.podarkes.domain.GameProgress;

import java.util.List;
import java.util.UUID;

public class GameController {
    public static GameRecord getGameById(String gameId) {
        return new GameRecord(UUID.randomUUID().toString(), GameProgress.ONGOING);
    }

    public static List<GameRecord> getAllGames() {
        return List.of();
    }

    public static GameRecord joinLobby(String gameId) {
        return new GameRecord(UUID.randomUUID().toString(), GameProgress.STARTING);
    }

    public static GameRecord startLobby(String request) {
        return new GameRecord(UUID.randomUUID().toString(), GameProgress.LOBBY);
    }

    public record GameRecord(String uuid, GameProgress gameProgress) {

    }
}
