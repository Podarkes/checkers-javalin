
package io.podarkes;

import io.javalin.Javalin;
import io.podarkes.config.DatabaseConfig;
import io.podarkes.controllers.GameController;
import io.podarkes.controllers.MoveController;
import io.podarkes.controllers.PlayerController;
import io.podarkes.player.GenericDao;
import io.podarkes.player.PlayerRecord;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start(7000);

        app.get("/games", ctx -> ctx.json(GameController.getAllGames()));
        app.put("/games", ctx -> ctx.json(GameController.joinLobby(ctx.body())));
        app.post("/games", ctx -> ctx.json(GameController.startLobby(ctx.body())));
        app.get("/games/{gameId}", ctx -> ctx.json(GameController.getGameById(ctx.pathParam("gameId"))));

        app.get("/games/{gameId}/moves", ctx -> ctx.json(MoveController.getMoves(ctx.body())));
        app.put("/games/{gameId}/moves", ctx -> ctx.json(MoveController.makeMove(ctx.body())));

        DataSource dataSource = DatabaseConfig.getDataSource();
        GenericDao<PlayerRecord> playerDao = new GenericDao<>(dataSource, PlayerRecord.class, "player");
        PlayerController playerController = new PlayerController(playerDao);

        app.get("/players", ctx -> ctx.json(playerController.getAllPlayers()));
        app.get("/players/{playerId}", ctx -> ctx.json(playerController.getPlayer(Long.valueOf(ctx.pathParam("playerId")))));
    }
}
