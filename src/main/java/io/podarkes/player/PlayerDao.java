package io.podarkes.player;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerDao {

    private final DataSource dataSource;

    public PlayerDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<PlayerRecord> getAllPlayers() {

        List<PlayerRecord> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM player");
            while (rs.next()) {
                result.add(new PlayerRecord(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public PlayerRecord getPlayerById(Long playerId) {

        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM player WHERE id = ?");
            ps.setLong(1, playerId);
            ResultSet resultSet = ps.executeQuery();
            resultSet.first();
            PlayerRecord playerRecord = new PlayerRecord(
                    resultSet.getLong("id"), resultSet.getString("name")
            );
            if (resultSet.next()) {
                throw new IllegalStateException("Result set contains more than one unique value");
            }
            return playerRecord;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}