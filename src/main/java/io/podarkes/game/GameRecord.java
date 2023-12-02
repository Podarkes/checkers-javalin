package io.podarkes.game;

import io.podarkes.domain.GameProgress;

public record GameRecord(Long id, GameProgress gameProgress, Long player1, Long player2) {

}
