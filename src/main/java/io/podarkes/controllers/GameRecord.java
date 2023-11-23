package io.podarkes.controllers;

import io.podarkes.domain.GameProgress;

public record GameRecord(Long gameId, GameProgress gameProgress) {

}
