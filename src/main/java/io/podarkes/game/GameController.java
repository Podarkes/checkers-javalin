package io.podarkes.game;

import io.podarkes.game.GameDao;
import io.podarkes.game.GameRecord;

import java.util.List;

public class GameController {

    private GameDao gameDao;

    public GameController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public GameRecord getGameById(Long gameId) {
        return gameDao.findById(gameId);
    }

    public List<GameRecord> getAllGames() {
        return gameDao.findAll();
    }

    public GameRecord joinLobby(Long playerId) {
        // TODO Implement
        return null;
    }

    public GameRecord startLobby(Long playerId) {
        return gameDao.createLobby(playerId);
    }
}
