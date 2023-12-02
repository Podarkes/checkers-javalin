package io.podarkes.move;

public class MoveController {
    public static MoveResponse getMoves(String moveRequest) {
        return new MoveResponse();
    }

    public static MoveResponse makeMove(String moveRequest) {
        return new MoveResponse();
    }

    public record MoveResponse() {

    }
}
