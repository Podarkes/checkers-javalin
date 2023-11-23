package io.podarkes.player;

import io.podarkes.controllers.GameRecord;
import io.podarkes.domain.GameProgress;

import javax.sql.DataSource;
import java.sql.*;


public class GameDao extends GenericDao<GameRecord> {

    public GameDao(DataSource dataSource, Class<GameRecord> type, String tableName) {
        super(dataSource, type, tableName);
    }

    public GameRecord createLobby(Long playerOneId) {

        // TODO Extract entity creation to GenericDao
        try (Connection con = dataSource.getConnection()) {

            String INSERT = STR. "INSERT INTO \{ tableName }(player_one_id, progress) values (?, ?)" ;
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, playerOneId);
            ps.setString(2, GameProgress.LOBBY.toString());

            int i = ps.executeUpdate();
            if (i == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return new GameRecord(rs.getLong(1),GameProgress.LOBBY);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalStateException();
    }
}
