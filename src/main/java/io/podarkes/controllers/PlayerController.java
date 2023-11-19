package io.podarkes.controllers;

import java.util.List;

public class PlayerController {
    public static List<PlayerResponse> getAllPlayers() {
        return List.of();
    }

    public static PlayerResponse getPlayer(Long playerId) {
        return new PlayerResponse();
    }

    public record PlayerResponse() {

    }
}
