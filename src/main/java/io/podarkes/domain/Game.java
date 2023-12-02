package io.podarkes.domain;

public record Game(Long id,
                   GameProgress progress,
                   Long player1,
                   Long player2) {

}
