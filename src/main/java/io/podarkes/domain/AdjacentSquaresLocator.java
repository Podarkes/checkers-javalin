package io.podarkes.domain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdjacentSquaresLocator {

    static List<Position> getAdjacentSquares(Integer square) {
        Integer[] ij = getIJ(square);
        Integer i = ij[0];
        Integer j = ij[1];

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(i - 1, j - 1));
        positions.add(new Position(i + 1, j - 1));
        positions.add(new Position(i - 1, j + 1));
        positions.add(new Position(i + 1, j + 1));

        return positions.stream().filter(AdjacentSquaresLocator::isWithinBoard).toList();
    }


    static List<Integer> getAdjacentSquaresNumbers(Integer square) {
        Integer[] ij = getIJ(square);
        Integer i = ij[0];
        Integer j = ij[1];

        List<Position> positions = new ArrayList<>();
        positions.add(new Position(i - 1, j - 1));
        positions.add(new Position(i + 1, j - 1));
        positions.add(new Position(i - 1, j + 1));
        positions.add(new Position(i + 1, j + 1));

        return positions.stream()
                .filter(AdjacentSquaresLocator::isWithinBoard)
                .toList().stream()
                .map(p -> Checkerboard.getAllSquaresArray()[p.i()][p.j()])
                .toList();
    }


    static Integer[] getIJ(Integer cell) {
        for (int i = 0; i < Checkerboard.getAllSquaresArray().length; i++) {
            int j = Arrays.asList(Checkerboard.getAllSquaresArray()[i]).indexOf(cell);
            if (j != -1) {
                return new Integer[]{i, j};
            }
        }
        throw new IllegalArgumentException("Can't find provided cell: " + cell);
    }

    static boolean isWithinBoard(Position position) {
        try {
            Integer i = Checkerboard.getAllSquaresArray()[position.i()][position.j()];
            return true;
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}
