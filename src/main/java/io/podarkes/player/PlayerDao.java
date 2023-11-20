package io.podarkes.player;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
            ResultSet rs = statement.executeQuery("SELECT * FROM players");


            while (rs.next()) {
                result.add(new PlayerRecord(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
