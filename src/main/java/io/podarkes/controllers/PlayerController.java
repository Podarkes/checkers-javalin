package io.podarkes.controllers;

import io.podarkes.player.GenericDao;
import io.podarkes.player.PlayerRecord;

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
