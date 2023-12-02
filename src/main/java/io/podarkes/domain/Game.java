package io.podarkes.domain;

public record Game(String id,
                   GameProgress progress,
                   Long player1,
                   Long player2) {

}
