package io.podarkes.domain;

public record Move(Game game,
                   Player player,
                   String side,
                   String move,
                   String dark,
                   String light) {

}
