package io.podarkes.domain;

public record Game(String id,
                   GameProgress progress,
                   Long playerOneId,
                   Long playerTwoId) {

}
