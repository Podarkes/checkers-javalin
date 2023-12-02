package io.podarkes.player;

import io.podarkes.persistence.GenericDao;

import java.util.List;

public class PlayerController {

    GenericDao<PlayerRecord> playerDao;

    public PlayerController(GenericDao<PlayerRecord> playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerRecord> getAllPlayers() {
        return playerDao.findAll();
    }

    public PlayerRecord getPlayer(Long playerId) {
        return playerDao.findById(playerId);
    }
}
