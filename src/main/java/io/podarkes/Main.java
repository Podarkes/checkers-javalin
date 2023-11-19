
package io.podarkes;

import io.javalin.Javalin;
import io.podarkes.controllers.GameController;
import io.podarkes.controllers.MoveController;
import io.podarkes.controllers.PlayerController;

public class Main {
    public static void main(String[] args) {
        Javalin app = Javalin.create().start(7000);

        app.get("/games", ctx -> ctx.json(GameController.getAllGames()));
        app.put("/games", ctx -> ctx.json(GameController.joinLobby(ctx.body())));
        app.post("/games", ctx -> ctx.json(GameController.startLobby(ctx.body())));
        app.get("/games/<gameId>", ctx -> ctx.json(GameController.getGameById(ctx.pathParam("gameId"))));

        app.get("/games/<gameId>/moves", ctx -> ctx.json(MoveController.getMoves(ctx.body())));
        app.put("/games/<gameId>/moves", ctx -> ctx.json(MoveController.makeMove(ctx.body())));

        app.get("/players", ctx -> ctx.json(PlayerController.getAllPlayers()));
        app.get("/players/{playerId}", ctx -> ctx.json(PlayerController.getPlayer(Long.valueOf(ctx.pathParam("playerId")))));
    }
}
