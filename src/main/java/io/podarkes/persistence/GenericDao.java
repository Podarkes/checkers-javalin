package io.podarkes.persistence;

import org.jetbrains.annotations.NotNull;

import javax.sql.DataSource;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GenericDao<T> {

    protected final DataSource dataSource;

    protected final Class<T> type;

    protected final String tableName;

    public GenericDao(DataSource dataSource, Class<T> type, String tableName) {
        this.dataSource = dataSource;
        this.type = type;
        this.tableName = tableName;
    }

    public List<T> findAll() {

        List<T> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {

            Constructor<T> constructor = (Constructor<T>) type.getDeclaredConstructors()[0];

            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(STR. "SELECT * FROM \{ tableName }" );

            while (rs.next()) {
                Object[] array = mapConstructorArgsFromResultSet(constructor, rs);
                result.add(constructor.newInstance(array));
            }
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    @NotNull
    private Object[] mapConstructorArgsFromResultSet(Constructor<T> constructor, ResultSet rs) throws SQLException {
        List<Object> list = new ArrayList<>();
        for (Parameter p : constructor.getParameters()) {
            Object o = readArgumentFromResultSet(p, rs);
            list.add(o);
        }
        return list.toArray();
    }

    private Object readArgumentFromResultSet(Parameter p, ResultSet rs) throws SQLException {
        return switch (p.getType().getCanonicalName()) {
            case "java.lang.Long" -> rs.getLong(p.getName());
            case "java.lang.String" -> rs.getString(p.getName());
            default -> throw new IllegalArgumentException(STR. "\{ p.getType() } is not supported yet." );
        };
    }

    public T findById(Long id) {
        Constructor<T> constructor = (Constructor<T>) type.getDeclaredConstructors()[0];

        try (Connection con = dataSource.getConnection()) {
            PreparedStatement ps = con.prepareStatement(STR. "SELECT * FROM \{ tableName } WHERE id = ?" );
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();
            rs.first();


            Object[] array = mapConstructorArgsFromResultSet(constructor, rs);
            T res = constructor.newInstance(array);

            if (rs.next()) {
                throw new IllegalStateException("Result set contains more than one unique value");
            }

            return res;
        } catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}