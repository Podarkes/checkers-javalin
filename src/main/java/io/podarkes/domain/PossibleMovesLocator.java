package io.podarkes.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PossibleMovesLocator {
    public Map<Integer, List<PossibleMove>> getPossibleMoves(Side side, State state) {

        if (state == null) {
            state = Checkerboard.getStartingState();
        }

        Checkerboard checkerboard = new Checkerboard(state.dark(), state.light());

        return switch (side) {
            case DARK -> getPossibleMoves(Side.DARK, checkerboard);
            case LIGHT -> getPossibleMoves(Side.LIGHT, checkerboard);
        };
    }

    private Map<Integer, List<PossibleMove>> getPossibleMoves(Side side, Checkerboard checkerboard) {

        var moves = new HashMap<Integer, List<PossibleMove>>();
        var maybeMoves = new HashMap<Integer, List<PossibleMove>>();

        for (Integer start : checkerboard.getSide(side)) {

            Square square = checkerboard.getSquareMap().get(start);

            // empty neighbors
            square.neighborSquares().stream()
                    .map(dest -> checkerboard.getSquareMap().get(dest))
                    .filter(dest -> dest.pieceType().equals(PieceType.EMPTY))
                    .forEach(dest -> moves
                            .computeIfAbsent(start, v -> new ArrayList<>())
                            .add(new PossibleMove(side, start, dest.number(), true, true)
                            ));

            // busy of same color - skip
            // busy of opponent color - maybe

            PieceType opponent;
            if (checkerboard.getSquareMap().get(start).pieceType().equals(PieceType.DARK)) {
                opponent = PieceType.LIGHT;
            } else {
                opponent = PieceType.DARK;
            }

            square.neighborSquares().stream()
                    .map(dest -> checkerboard.getSquareMap().get(dest))
                    .filter(dest -> dest.pieceType().equals(opponent))
                    .forEach(dest -> maybeMoves
                            .computeIfAbsent(start, v -> new ArrayList<>())
                            .add(new PossibleMove(side, start, dest.number(), null, null)
                            ));
        }

        for (var entry : maybeMoves.entrySet()) {
            for (PossibleMove possibleMove : entry.getValue()) {

                Square square = checkerboard.getSquareMap().get(possibleMove.destination());

                // empty neighbors
                square.neighborSquares().stream()
                        .map(captureDest -> checkerboard.getSquareMap().get(captureDest))
                        .filter(captureDest -> captureDest.pieceType().equals(PieceType.EMPTY))
                        .forEach(captureDest -> moves
                                .computeIfAbsent(possibleMove.position(), v -> new ArrayList<>())
                                .add(new PossibleMove(side, possibleMove.position(), captureDest.number(), true, null)
                                ));
            }
        }

        return moves;
    }
}
