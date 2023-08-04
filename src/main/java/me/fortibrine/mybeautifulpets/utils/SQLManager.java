package me.fortibrine.mybeautifulpets.utils;

import org.bukkit.entity.EntityType;

import java.io.Closeable;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLManager implements Closeable {

    private Connection connection;

    public SQLManager() {
        try {
            String url = "jdbc:sqlite:/plugins/mobs.db";

            connection = DriverManager.getConnection(url);

            PreparedStatement preparedStatement = connection.prepareStatement("CREATE TABLE IF NOT EXISTS entities (" +
                    "uuid TEXT," +
                    "entity TEXT)");

            preparedStatement.executeUpdate();
            preparedStatement.close();

        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }

    }

    public void saveMob(String uuid, EntityType entityType) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO entities(uuid, entity) VALUES(?,?) ");
            preparedStatement.setString(1, uuid);
            preparedStatement.setString(2, entityType.name());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ignored) {

        }
    }

    public List<EntityType> getMobs(String uuid) {
        try {

            List<EntityType> entities = new ArrayList<>();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT entity FROM entities WHERE uuid = ?");
            preparedStatement.setString(1, uuid);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                entities.add(EntityType.fromName(resultSet.getString(1)))
            }

            resultSet.close();
            preparedStatement.close();

            return entities;

        } catch (SQLException exception) {
            return null;
        }
    }

    @Override
    public void close() {
        try {
            connection.close();
        } catch (SQLException ignored) {

        }
    }
}
