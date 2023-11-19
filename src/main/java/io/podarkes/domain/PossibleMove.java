package io.podarkes.domain;

public record PossibleMove(Side side,
                           int position,
                           int destination,
                           Boolean isCapture,
                           Boolean isTerminal) {
}
