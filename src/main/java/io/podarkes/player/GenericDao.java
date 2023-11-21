package io.podarkes.player;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    private final DataSource dataSource;

    private final Class<T> type;

    private final String tableName;

    public GenericDao(DataSource dataSource, Class<PlayerRecord> type, String tableName) {
        this.dataSource = dataSource;
        this.type = (Class<T>) type;
        this.tableName = tableName;
    }

    public List<T> findAll() {

        List<T> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(STR. "SELECT * FROM \{ tableName }" );
            while (rs.next()) {
                result.add(
                        (T) type.getDeclaredConstructors()[0].newInstance(rs.getLong("id"), rs.getString("name"))
                );
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public T findById(Long id) {
        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(STR. "SELECT * FROM \{ tableName } WHERE id = ?" );
            ps.setLong(1, id);

            ResultSet resultSet = ps.executeQuery();
            resultSet.first();

            T res = (T) type.getDeclaredConstructors()[0]
                    .newInstance(resultSet.getLong("id"), resultSet.getString("name"));

            if (resultSet.next()) {
                throw new IllegalStateException("Result set contains more than one unique value");
            }

            return res;
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}