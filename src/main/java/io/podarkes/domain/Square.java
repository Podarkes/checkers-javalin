package io.podarkes.domain;

import java.util.List;

public record Square(Integer number, PieceType pieceType, List<Integer> neighborSquares) {

}
