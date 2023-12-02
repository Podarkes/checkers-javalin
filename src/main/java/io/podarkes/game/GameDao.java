package io.podarkes.game;

import io.podarkes.domain.GameProgress;
import io.podarkes.persistence.GenericDao;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class GameDao extends GenericDao<GameRecord> {

    public GameDao(DataSource dataSource, Class<GameRecord> type, String tableName) {
        super(dataSource, type, tableName);
    }

    public GameRecord createLobby(Long player1) {

        // TODO Extract entity creation to GenericDao
        try (Connection con = dataSource.getConnection()) {

            String INSERT = STR. "INSERT INTO \{ tableName }(player1, progress) values (?, ?)" ;
            PreparedStatement ps = con.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, player1);
            ps.setString(2, GameProgress.LOBBY.toString());

            int i = ps.executeUpdate();
            if (i == 1) {
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    return new GameRecord(rs.getLong(1), GameProgress.LOBBY, player1, null);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        throw new IllegalStateException();
    }
}
