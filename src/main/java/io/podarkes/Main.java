
package io.podarkes;

import io.javalin.Javalin;
import io.podarkes.persistence.DatabaseConfig;
import io.podarkes.game.GameController;
import io.podarkes.game.GameRecord;
import io.podarkes.move.MoveController;
import io.podarkes.player.PlayerController;
import io.podarkes.game.GameDao;
import io.podarkes.persistence.GenericDao;
import io.podarkes.player.PlayerRecord;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {

        Javalin app = Javalin.create().start(7000);

        DataSource dataSource = DatabaseConfig.getDataSource();
        GameDao gameDao = new GameDao(dataSource, GameRecord.class, "game");
        GameController gameController = new GameController(gameDao);

        app.get("/games", ctx -> ctx.json(gameController.getAllGames()));
        app.put("/games", ctx -> ctx.json(gameController.joinLobby(Long.valueOf(ctx.queryParam("gameId")))));
        app.post("/games", ctx -> ctx.json(gameController.startLobby(Long.valueOf(ctx.queryParam("playerId")))));
        app.get("/games/{gameId}", ctx -> ctx.json(gameController.getGameById(Long.valueOf(ctx.pathParam("gameId")))));

        app.get("/games/{gameId}/moves", ctx -> ctx.json(MoveController.getMoves(ctx.body())));
        app.put("/games/{gameId}/moves", ctx -> ctx.json(MoveController.makeMove(ctx.body())));

        GenericDao<PlayerRecord> playerDao = new GenericDao<>(dataSource, PlayerRecord.class, "player");
        PlayerController playerController = new PlayerController(playerDao);

        app.get("/players", ctx -> ctx.json(playerController.getAllPlayers()));
        app.get("/players/{playerId}", ctx -> ctx.json(playerController.getPlayer(Long.valueOf(ctx.pathParam("playerId")))));
    }
}
