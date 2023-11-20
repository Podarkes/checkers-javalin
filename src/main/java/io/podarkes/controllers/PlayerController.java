package io.podarkes.controllers;

import io.podarkes.player.PlayerDao;
import io.podarkes.player.PlayerRecord;

import java.util.List;

public class PlayerController {

    PlayerDao playerDao;

    public PlayerController(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    public List<PlayerRecord> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    public PlayerRecord getPlayer(Long playerId) {
        return new PlayerRecord(1L, "Alice");
    }
}
