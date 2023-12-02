package io.podarkes.game;

import java.util.List;

public class GameController {
    private final GameDao gameDao;

    public GameController(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    public GameRecord getGameById(Long gameId) {
        return gameDao.findById(gameId);
    }

    public List<GameRecord> getAllGames() {
        return gameDao.findAll();
    }

    public GameRecord joinLobby(Long gameId, Long playerId) {
        return gameDao.joinLobby(gameId, playerId);
    }

    public GameRecord startLobby(Long playerId) {
        return gameDao.createLobby(playerId);
    }
}
