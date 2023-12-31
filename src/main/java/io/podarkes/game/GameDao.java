package io.podarkes.game;

import io.podarkes.domain.GameProgress;
import io.podarkes.persistence.GenericDao;

import javax.sql.DataSource;
import java.sql.*;


public class GameDao extends GenericDao<GameRecord> {

    public GameDao(DataSource dataSource, Class<GameRecord> type, String tableName) {
        super(dataSource, type, tableName);
    }

    public GameRecord createLobby(Long player1) {
        try (Connection con = dataSource.getConnection()) {

            String INSERT = STR. "INSERT INTO \{ tableName }(player1, progress) values (?, ?)" ;
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, player1);
            ps.setString(2, GameProgress.LOBBY.toString());

            int i = ps.executeUpdate();
            if (i == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return new GameRecord(rs.getLong(1), GameProgress.LOBBY.toString(), player1, null);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalStateException();
    }

    public GameRecord joinLobby(Long gameId, Long playerId) {

        try (Connection con = dataSource.getConnection()) {

            String UPDATE = STR. "UPDATE \{ tableName } SET player2 = ?, progress = ? WHERE id = ?" ;
            PreparedStatement ps = con.prepareStatement(UPDATE);
            ps.setLong(1, playerId);
            ps.setString(2, GameProgress.STARTING.toString());
            ps.setLong(3, gameId);
            ps.executeUpdate();
            return this.findById(gameId);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
